package com.pcms.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pc {
    private int pc_id;
    private String name;
    private String serial_number;
    private PcStatus status;

    public Pc(int pc_id, String name, String serial_number, String status) {
        this(pc_id, name, serial_number, PcStatus.fromString(status));
    }


    public void setStatus(String status) {
        this.status = PcStatus.fromString(status);
    }
}
