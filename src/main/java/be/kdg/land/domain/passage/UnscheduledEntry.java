package be.kdg.land.domain.passage;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;


@Entity
@DiscriminatorValue("UNSCHEDULED_ENTRY")
public class UnscheduledEntry extends Entry {

    public UnscheduledEntry() {
    }

    public UnscheduledEntry(LocalDateTime timeStamp, String licensePlate) {
        super(timeStamp, licensePlate);
    }
}
