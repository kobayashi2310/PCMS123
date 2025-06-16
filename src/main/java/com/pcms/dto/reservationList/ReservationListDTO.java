package com.pcms.dto.reservationList;

import java.util.List;

public record ReservationListDTO(
        int pc_id,
        String pc_name,
        List<String> situation,
        ReservationListStatus status
) {
}
