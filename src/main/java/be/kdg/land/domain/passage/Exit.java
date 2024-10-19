package be.kdg.land.domain.passage;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.time.LocalDateTime;


@Entity
@DiscriminatorValue("EXIT")
public class Exit extends Passage {
    public Exit() {
        super();
    }

    public Exit(LocalDateTime timeStamp, String licensePlate) {
        super(timeStamp, licensePlate);
    }
}
