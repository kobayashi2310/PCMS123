package com.pcms.model;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    private int reservation_id;
    private int user_id;
    private int pc_id;
    private LocalDate date;
    private byte period_number;
    private String reason;
    private ReservationStatus status;
    private LocalDateTime created_at;
    private Integer approver;
    private LocalDateTime approved_time;

    public Reservation(int user_id, int pc_id, LocalDate date, byte period_number, String reason) {
        this(-1, user_id, pc_id, date, period_number, reason,
                null, null, null, null);
    }

    public Reservation(int reservation_id, int user_id,
                       int pc_id, LocalDate date, byte period_number,
                       String reason, String status,
                       LocalDateTime created_at) {
        this(reservation_id, user_id, pc_id, date, period_number,
                reason, ReservationStatus.fromString(status), created_at, null, null);
    }

    public void setStatus(String status) {
        this.status = ReservationStatus.fromString(status);
    }
}
