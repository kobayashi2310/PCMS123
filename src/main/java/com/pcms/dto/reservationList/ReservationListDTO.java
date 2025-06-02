package com.pcms.dto.reservationList;

public record ReservationListDTO(
        int pc_id,
        String pc_name,
        ReservationListStatus status
) { }
