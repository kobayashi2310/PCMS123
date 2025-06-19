package com.pcms.dto.reservation;

import java.time.LocalDate;
import java.util.List;

public record ReservationDTO(
    List<PcDTO> pcList,
    LocalDate localDate,
    List<Boolean> periodList
) { }
