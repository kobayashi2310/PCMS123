package com.pcms.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalTime;

@Entity
@Table(name = "period")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Period {
    @Id
    @Column(name = "period_number")
    private byte period_number;

    @Column(name = "start_time", nullable = false)
    private LocalTime start_time;

    @Column(name = "end_time", nullable = false)
    private LocalTime end_time;
}
