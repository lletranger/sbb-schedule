package org.tsys.sbb.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

public @Data class ScheduleDto implements Serializable {

    private String name;
    private List<BoardDto> from;
    private List<BoardDto> to;
}