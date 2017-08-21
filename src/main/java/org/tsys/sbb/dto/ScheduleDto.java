package org.tsys.sbb.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

public @Data class ScheduleDto implements Serializable {

    String name;
    List<BoardDto> from;
    List<BoardDto> to;
}
