package be.kdg.land.domain.passage;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;


@Entity
@DiscriminatorValue("EXIT")
public class Exit extends Passage {
    public Exit() {
    }

    public Exit(LocalDateTime timeStamp, String licensePlate) {
        super(timeStamp, licensePlate);
    }
}
