package com.pcms.dto.reservationList;

import java.time.LocalDate;
import java.time.LocalTime;

public record ReservationListBuilder(
        int pc_id,
        String pc_name,
        String pc_status,
        LocalDate date,
        String name,
        Byte period_number,
        LocalTime start_time,
        LocalTime end_time
) {
}
