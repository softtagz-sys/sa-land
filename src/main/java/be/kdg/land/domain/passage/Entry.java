package be.kdg.land.domain.passage;

import jakarta.persistence.Entity;
import java.time.LocalDateTime;

@Entity
public abstract class Entry extends Passage {

    public Entry() {
    }

    public Entry(LocalDateTime timeStamp, String licensePlate) {
        super(timeStamp, licensePlate);
    }
}
