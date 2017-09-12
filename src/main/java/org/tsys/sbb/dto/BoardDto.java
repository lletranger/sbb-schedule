package org.tsys.sbb.dto;

import lombok.Data;

import java.io.Serializable;

public @Data class BoardDto implements Serializable {

    private int id;
    private String name;
    private String from;
    private String to;
    private String departure;
    private int distance;
    private int averageSpeed;
    private String journeyTime;
    private String expectedArrival;
    private String delay;
    private String arrival;
    private int seatsLeft;
    private String canAddDelay;
    private boolean ticketsAvailable;
    private int from_id;
    private int to_id;
    private int train_id;
    private String status;

    @Override
    public String toString() {
        return "To /".concat(to).concat(" departing at ").concat(departure);
    }
}