package com.pcms.model;

import lombok.Getter;

@Getter
public enum PcStatus {
    AVAILABLE("available"),
    MAINTENANCE("maintenance"),
    EXCLUSIVE_USE("exclusive_use");

    private final String value;

    PcStatus(String value) {
        this.value = value;
    }

    public static PcStatus fromString(String value) {
        for (PcStatus status : PcStatus.values()) {
            if (status.value.toLowerCase().equals(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("No matching constant for [" + value + "]");
    }

    @Override
    public String toString() {
        return value;
    }
}
