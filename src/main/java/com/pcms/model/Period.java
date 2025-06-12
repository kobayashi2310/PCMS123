package com.pcms.model;

import lombok.*;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Period {
    private byte period_number;
    private LocalTime start_time;
    private LocalTime end_time;
}
