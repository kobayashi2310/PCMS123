package com.pcms.dto.reservation;

import com.pcms.model.Pc;

import java.time.LocalDate;
import java.util.List;

public record ReservationFormDTO(
        Pc pc,
        List<ReservationPeriodDTO> reservationPeriods,
        LocalDate localDate
) { }
