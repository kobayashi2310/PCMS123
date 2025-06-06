package com.pcms.dto.reservation;

import java.time.LocalTime;

public record ReservationPeriodDTO(
        byte period_number,
        LocalTime localTime,
        LocalTime endTime
) {
}
