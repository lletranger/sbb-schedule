package org.tsys.sbb.dto;

import lombok.Data;

import javax.ejb.Singleton;
import java.io.Serializable;
import java.util.List;

@Singleton
public @Data class ScheduleDto implements Serializable {

    String name;
    List<BoardDto> from;
    List<BoardDto> to;
}
