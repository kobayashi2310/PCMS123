package com.pcms.dto.mypage.history;

import lombok.NonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * ユーザーの予約履歴を表すデータ転送オブジェクト（DTO）。
 * このレコードは、
 * ユーザーの特定の予約に関する詳細情報を簡略化された形式でカプセル化するために使用されます。
 */
public record ReservationHistoryDto(
        String pcName,
        @NonNull
        LocalDate reservationDate,
        LocalDateTime returnTime,
        String status
) {

    public String returnTimeStr() {
        return returnTime == null
                ? "-"
                : returnTime.toString();
    }

}
