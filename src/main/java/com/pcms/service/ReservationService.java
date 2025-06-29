package com.pcms.service;

import com.pcms.dataaccess.mapper.UserMapper;
import com.pcms.dataaccess.repository.PeriodRepository;
import com.pcms.dto.reservation.PcDTO;
import com.pcms.dto.reservation.ReservationCheckDTO;
import com.pcms.dto.reservation.ReservationDTO;
import com.pcms.dto.reservationList.ReservationListBuilder;
import com.pcms.dto.reservationList.ReservationListDTO;
import com.pcms.dto.reservationList.ReservationListStatus;
import com.pcms.dataaccess.mapper.PcMapper;
import com.pcms.dataaccess.mapper.ReservationMapper;
import com.pcms.model.PcStatus;
import com.pcms.model.Period;
import com.pcms.model.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * PC予約に関するサービスクラス。
 * 予約情報の取得、予約可能状態の判定などを行う。
 */
@Service
@RequiredArgsConstructor
public class ReservationService {

    private final UserMapper userMapper;
    private final PeriodRepository periodRepository;
    private final ReservationMapper reservationMapper;
    private final PcMapper pcMapper;

    /**
     * 指定された日付の全PCの予約状況を取得する。
     *
     * @param date 確認対象の日付
     * @return PCのIDをキーとした予約情報のマップ
     */
    public Map<Integer, ReservationListDTO> getReservation(LocalDate date) {
        List<ReservationListBuilder> list = reservationMapper.findByDate(date.toString());

        return list.stream()
                .collect(Collectors.groupingBy(
                        ReservationListBuilder::pc_id,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                builders -> {
                                    var firstBuilder = builders.getFirst();

                                    if (PcStatus.MAINTENANCE.getValue().equals(firstBuilder.pc_status())) {
                                        return new ReservationListDTO(
                                                firstBuilder.pc_id(),
                                                firstBuilder.pc_name(),
                                                null,
                                                ReservationListStatus.NOT_AVAILABLE
                                        );
                                    }

                                    ReservationListStatus status = determineStatus(builders);

                                    List<String> situation = builders.stream()
                                            .filter(b -> b.period_number() != null && b.name() != null)
                                            .map(b -> b.period_number() + "限:" + b.name())
                                            .toList();

                                    return new ReservationListDTO(
                                            firstBuilder.pc_id(),
                                            firstBuilder.pc_name(),
                                            situation.isEmpty() ? null : situation,
                                            status
                                    );
                                }
                        )
                ));
    }

    /**
     * 特定のPCに対する指定日付の予約状況と、PC一覧を取得する。
     *
     * @param pc_id 確認対象のPC ID
     * @param date  確認対象の日付
     * @return ReservationDTO オブジェクト（PC一覧・日付・各時限の空き状況）
     */
    public ReservationDTO getReservation(String pc_id, LocalDate date) {

        if (pc_id != null && !pc_id.isEmpty()) {
            var reservationList = reservationMapper.findByIdAndDate(pc_id, date.toString());

            List<Period> periodList = periodRepository.findAll();
            Map<Byte, Boolean> periodMap = new TreeMap<>();
            for (Period period : periodList) {
                periodMap.put(period.getPeriod_number(), true);
            }
            reservationList.forEach(r -> periodMap.put(r.getPeriod_number(), false));

            var pcDTOList = pcMapper.findByReservation().stream()
                    .map(p -> new PcDTO(p.getPc_id(), p.getName()))
                    .toList();

            return new ReservationDTO(pcDTOList, date, periodMap);

        } else {

            var pcDTOList = pcMapper.findByReservation().stream()
                    .map(p -> new PcDTO(p.getPc_id(), p.getName()))
                    .toList();

            return new ReservationDTO(pcDTOList, date, null);

        }

    }

    public ReservationCheckDTO checkReservation(int pc_id, LocalDate date, String otherPurpose, List<Byte> periods) {

        List<List<Byte>> groups = getGroups(periods
                                                .stream()
                                                .sorted()
                                                .toList());

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        List<String> startTimes = new ArrayList<>();
        List<String> endTimes = new ArrayList<>();

        for (List<Byte> group : groups) {
            startTimes.add(startTime(group.getFirst()).format(timeFormatter));
            endTimes.add(endTime(group.getLast()).format(timeFormatter));
        }

        return new ReservationCheckDTO(pc_id, date, otherPurpose, groups, startTimes, endTimes);

    }

    /**
     * 特定の目的のために、特定の日付と時間帯に、指定されたユーザー用に PC を予約します
     *
     * @param email PCを予約するユーザーのメールアドレス
     * @param pc_id 予約するPCのID
     * @param date 予約日
     * @param otherPurpose 予約の説明または目的
     * @param periods 予約が行われた期間を表す期間IDのリスト
     * @throws UsernameNotFoundException 引数のメールアドレスがどのユーザーとも一致しない場合
     * @throws IllegalStateException 予約プロセスが失敗した場合、または要求された期間をすべて登録できない場合
     */
    @Transactional
    public void reserve(String email, int pc_id, LocalDate date, String otherPurpose, List<Byte> periods) {

        int user_id = userMapper.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Email not found:" + email))
                .getUser_id();

        List<Reservation> reservations = periods.stream()
                .sorted()
                .map(id -> new Reservation(user_id, pc_id, date, id, otherPurpose))
                .toList();

        int result = reservationMapper.reserve(reservations);
        if (result != periods.size()) {
            throw new IllegalStateException("予約が登録できませんでした");
        }

    }

    /**
     * 指定されたリストから連続するバイトを個別のサブリストにグループ化します。
     * 各サブリストには連続するバイトのシーケンスが含まれます。
     *
     * @param periodInts 連続するサブリストにグループ化されるバイトのリスト
     * @return サブリストのリスト。各サブリストには連続するバイトが含まれる
     */
    private static List<List<Byte>> getGroups(List<Byte> periodInts) {
        List<List<Byte>> groups = new ArrayList<>();
        List<Byte> currentGroup = new ArrayList<>();
        byte prev = 10;

        for (byte p : periodInts) {
            if (p == prev + 1) {
                currentGroup.add(p);
            } else {
                if (!currentGroup.isEmpty())
                    groups.add(currentGroup);
                currentGroup = new ArrayList<>();
                currentGroup.add(p);
            }
            prev = p;
        }

        if (!currentGroup.isEmpty())
            groups.add(currentGroup);
        return groups;
    }

    /**
     * 仮実装。指定されたコマに対応する授業開始時間を返す。
     * @param period コマ
     * @return 指定されたコマに対応する授業開始時間
     */
    private LocalTime startTime(int period) {
        return switch (period) {
            case 1 -> LocalTime.of(9, 0);
            case 2 -> LocalTime.of(10, 40);
            case 3 -> LocalTime.of(13, 0);
            case 4 -> LocalTime.of(14, 30);
            case 5 -> LocalTime.of(16, 10);
            default -> LocalTime.of(0, 0);
        };
    }

    /**
     * 仮実装。指定されたコマに対応する授業終了時間を返す。
     * @param period コマ
     * @return 指定されたコマに対応する授業終了時間
     */
    private LocalTime endTime(int period) {
        return switch (period) {
            case 1 -> LocalTime.of(10, 30);
            case 2 -> LocalTime.of(12, 10);
            case 3 -> LocalTime.of(14, 20);
            case 4 -> LocalTime.of(16, 0);
            case 5 -> LocalTime.of(17, 40);
            default -> LocalTime.of(0, 0);
        };
    }

    /**
     * PCの予約データに基づき、現在のステータス（使用中・予約可など）を判定する。
     *
     * @param builders 特定PCに関する予約データ一覧
     * @return ReservationListStatus 判定されたステータス
     */
    private ReservationListStatus determineStatus(List<ReservationListBuilder> builders) {
        boolean hasReservations = builders.stream()
                .anyMatch(b -> b.date() != null);

        if (!hasReservations) {
            return ReservationListStatus.AVAILABLE_FOR_RESERVATION;
        }

        LocalTime currentTime = LocalTime.now();
        LocalDate today = LocalDate.now();

        boolean isCurrentlyInUse = builders.stream()
                .anyMatch(b -> b.date() != null &&
                        today.equals(b.date()) &&
                        b.start_time().isBefore(currentTime) &&
                        b.end_time().isAfter(currentTime));

        if (isCurrentlyInUse) {
            return ReservationListStatus.CURRENTLY_IN_USE;
        }

        long reservationCount = builders.stream()
                .filter(b -> b.date() != null)
                .count();

        if (reservationCount >= getMaxPeriodsPerDay()) {
            return ReservationListStatus.NO_VACANCIES;
        }

        return ReservationListStatus.RESERVATION_AVAILABLE;
    }

    /**
     * 1日あたりの最大予約可能時限数を返す。
     * 将来的にコマ数が変更される場合に備え、定数化している。
     *
     * @return 最大予約可能時限数（現在は5）
     */
    private int getMaxPeriodsPerDay() {
        return 5;
    }
}
