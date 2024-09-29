package be.kdg.land.domain.passage;


import jakarta.persistence.MappedSuperclass;

import java.time.LocalDateTime;

@MappedSuperclass
public abstract class Entry extends Passage {

    public Entry() {
    }

    public Entry(LocalDateTime timeStamp, String licensePlate) {
        super(timeStamp, licensePlate);
    }
}
