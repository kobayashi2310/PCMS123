package com.pcms.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pc {
    private int pc_id;
    private String name;
    private String serial_number;
    private PcStatus status;

    public Pc() {
    }

    public Pc(int pc_id, String name, String serial_number, String status) {
        this(pc_id, name, serial_number, PcStatus.fromString(status));
    }

    public Pc(int pc_id, String name, String serial_number, PcStatus status) {
        this.pc_id = pc_id;
        this.name = name;
        this.serial_number = serial_number;
        this.status = status;
    }

    public void setStatus(String status) {
        this.status = PcStatus.fromString(status);
    }
}
