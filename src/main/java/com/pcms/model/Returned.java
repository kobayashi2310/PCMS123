package com.pcms.model;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Returned {
    private int returned_id;
    private int reservation_id;
    private LocalDateTime returned_time;
    private String comment;
}
