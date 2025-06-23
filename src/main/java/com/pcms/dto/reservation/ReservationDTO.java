package com.pcms.dto.reservation;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public record ReservationDTO(
    List<PcDTO> pcList,
    LocalDate localDate,
    Map<Byte, Boolean> period
) { }
