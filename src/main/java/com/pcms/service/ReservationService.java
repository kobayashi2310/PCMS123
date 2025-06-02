package com.pcms.service;

import com.pcms.dto.reservationList.ReservationListBuilder;
import com.pcms.dto.reservationList.ReservationListDTO;
import com.pcms.dto.reservationList.ReservationListStatus;
import com.pcms.model.PcStatus;
import com.pcms.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * PCの予約状況を管理するサービスクラスです。
 * このクラスは以下の機能を提供します：
 * - 指定された日付のPC予約状況の取得
 * - PCの利用可能状態の判定
 * - 予約状況に基づくPCのステータス管理
 */
@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;

    /**
     * 指定された日付のPC予約状況を取得します。
     *
     * @param date 予約状況を確認する日付
     * @return PC_IDをキーとし、各PCの予約状況をマッピングしたMap
     */
    public Map<Integer, ReservationListDTO> getReservation(LocalDate date) {
        List<ReservationListBuilder> list = reservationRepository.findByDate(date.toString());

        // PCごとにグループ化して予約状況を解析
        return list.stream()
                .collect(Collectors.groupingBy(
                        ReservationListBuilder::pc_id,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                builders -> {
                                    var firstBuilder = builders.getFirst();

                                    if (PcStatus.MAINTENANCE.toString().equals(firstBuilder.pc_status())) {
                                        return new ReservationListDTO(
                                                firstBuilder.pc_id(),
                                                firstBuilder.pc_name(),
                                                ReservationListStatus.NOT_AVAILABLE
                                        );
                                    }

                                    ReservationListStatus status = determineStatus(builders);

                                    return new ReservationListDTO(
                                            firstBuilder.pc_id(),
                                            firstBuilder.pc_name(),
                                            status
                                    );
                                }
                        )
                ));
    }

    /**
     * PCの予約状況から現在の状態を判定します。
     *
     * @param builders 特定のPCの予約情報リスト
     * @return PCの現在の状態
     */
    private ReservationListStatus determineStatus(List<ReservationListBuilder> builders) {
        // 予約の有無をチェック
        boolean hasReservations = builders.stream()
                .anyMatch(b -> b.date() != null);

        // 予約が1件もない場合は予約可能
        if (!hasReservations) {
            return ReservationListStatus.AVAILABLE_FOR_RESERVATION;
        }

        // システム日時を取得
        LocalTime currentTime = LocalTime.now();
        LocalDate today = LocalDate.now();

        // 現在使用中かどうかをチェック
        boolean isCurrentlyInUse = builders.stream()
                .anyMatch(b -> b.date() != null &&
                        today.equals(b.date()) &&  // 今日の予約か
                        b.start_time().isBefore(currentTime) &&  // 開始時刻を過ぎている
                        b.end_time().isAfter(currentTime));      // 終了時刻前

        if (isCurrentlyInUse) {
            return ReservationListStatus.CURRENTLY_IN_USE;
        }

        // 予約数が上限に達しているかチェック
        long reservationCount = builders.stream()
                .filter(b -> b.date() != null)
                .count();

        if (reservationCount >= getMaxPeriodsPerDay()) {
            return ReservationListStatus.NO_VACANCIES;
        }

        // 予約はあるが、まだ空きがある状態
        return ReservationListStatus.RESERVATION_AVAILABLE;
    }

    /**
     * 1日あたりの最大予約可能時限数を取得します。
     *
     * @return 最大予約可能時限数
     */
    private int getMaxPeriodsPerDay() {
        return 4;
    }
}