package org.tsys.sbb.dto;

import lombok.Data;

import java.io.Serializable;

public @Data class StationDto implements Serializable {

    private int id;
    private String name;
}
