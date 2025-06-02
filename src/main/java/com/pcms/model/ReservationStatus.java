package com.pcms.model;

import lombok.Getter;

@Getter
public enum ReservationStatus {
    PENDING_APPROVAL("pending_approval"),
    APPROVED("approved"),
    CANCELED("canceled");

    private final String value;

    ReservationStatus(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public static ReservationStatus fromString(String value) {
        for (ReservationStatus status : ReservationStatus.values()) {
            if (status.value.toLowerCase().equals(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("No matching constant for [" + value + "]");
    }
}
