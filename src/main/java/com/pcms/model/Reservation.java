package com.pcms.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class Reservation {
    private int reservation_id;
    private int user_id;
    private int pc_id;
    private LocalDate date;
    private byte period_number;
    private String reason;
    private ReservationStatus status;
    private LocalDateTime created_at;
    private int approver;
    private LocalDateTime approved_time;

    public Reservation() {
    }

    public Reservation(int reservation_id, int user_id,
                       int pc_id, LocalDate date, byte period_number,
                       String reason, String status,
                       LocalDateTime created_at) {
        this(reservation_id, user_id, pc_id, date, period_number,
                reason, ReservationStatus.fromString(status), created_at, null, null);
    }

    public Reservation(int reservation_id, int user_id,
                       int pc_id, LocalDate date, byte period_number,
                       String reason, ReservationStatus status,
                       LocalDateTime created_at, Integer approver,
                       LocalDateTime approved_time) {
        this.reservation_id = reservation_id;
        this.user_id = user_id;
        this.pc_id = pc_id;
        this.date = date;
        this.period_number = period_number;
        this.reason = reason;
        this.status = status;
        this.created_at = created_at;
        this.approver = approver;
        this.approved_time = approved_time;
    }

    public void setStatus(String status) {
        this.status = ReservationStatus.fromString(status);
    }
}
