package be.kdg.land.domain.passage;

import be.kdg.land.domain.Truck;

import java.time.LocalDateTime;

public class Passage {
    private final LocalDateTime timeStamp;
    private final Gate gate;
    private final Truck truck;

    public Passage(LocalDateTime timeStamp, Gate gate, Truck truck) {
        this.timeStamp = timeStamp;
        this.gate = gate;
        this.truck = truck;
    }
}
