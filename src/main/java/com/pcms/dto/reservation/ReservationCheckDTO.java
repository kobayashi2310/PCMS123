package com.pcms.dto.reservation;

import java.time.LocalDate;
import java.util.List;

public record ReservationCheckDTO(
    int pc_id,
    LocalDate localDate,
    String otherPurpose,
    List<List<Byte>> periodGroups,
    List<String> startTimes,
    List<String> endTimes
) { }
