package com.pcms.service;

import com.pcms.dataaccess.repository.PeriodRepository;
import com.pcms.dto.reservation.PcDTO;
import com.pcms.dto.reservation.ReservationDTO;
import com.pcms.dto.reservationList.ReservationListBuilder;
import com.pcms.dto.reservationList.ReservationListDTO;
import com.pcms.dto.reservationList.ReservationListStatus;
import com.pcms.dataaccess.mapper.PcMapper;
import com.pcms.dataaccess.mapper.ReservationMapper;
import com.pcms.model.PcStatus;
import com.pcms.model.Period;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * PC予約に関するサービスクラス。
 * 予約情報の取得、予約可能状態の判定などを行う。
 */
@Service
@RequiredArgsConstructor
public class ReservationService {

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
