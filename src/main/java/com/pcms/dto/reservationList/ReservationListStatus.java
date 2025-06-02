package com.pcms.dto.reservationList;

import lombok.Getter;

@Getter
public enum ReservationListStatus {
    AVAILABLE_FOR_RESERVATION("予約可能"),
    RESERVATION_AVAILABLE("予約あり"),
    NO_VACANCIES("空き無し"),
    CURRENTLY_IN_USE("使用中"),
    NOT_AVAILABLE("使用不可");

    private final String value;

    ReservationListStatus(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
