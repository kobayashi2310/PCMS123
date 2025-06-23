package com.pcms.dto.reservation;

import java.time.LocalDate;
import java.util.List;

public record ReservationCheckDTO(
    String pc_id,
    LocalDate localDate,
    String otherPurpose,
    List<List<Integer>> periodGroups,
    List<String> startTimes,
    List<String> endTimes
) { }
