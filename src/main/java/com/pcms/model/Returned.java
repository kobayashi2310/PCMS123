package com.pcms.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Returned {
    private int returned_id;
    private int reservation_id;
    private LocalDateTime returned_time;
    private String comment;

    public Returned() {
    }

    public Returned(int returned_id, int reservation_id, LocalDateTime returned_time, String comment) {
        this.returned_id = returned_id;
        this.reservation_id = reservation_id;
        this.returned_time = returned_time;
        this.comment = comment;
    }
}
