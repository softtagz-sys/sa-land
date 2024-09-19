package be.kdg.land.domain.passage;

import be.kdg.land.domain.Truck;

import java.time.LocalDateTime;

public class Exit extends Passage {
    public Exit(LocalDateTime timeStamp, Gate gate, Truck truck) {
        super(timeStamp, gate, truck);
    }
}
