package be.kdg.land.domain.passage;


import be.kdg.land.domain.appointment.Appointment;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;


@Entity
@DiscriminatorValue("SCHEDULED_ENTRY")
public class ScheduledEntry extends Entry {

    @NotNull(message = "You can only enter if you provide an appointment")
    @OneToOne
    private Appointment appointment;

    public ScheduledEntry() {
    }

    public ScheduledEntry(LocalDateTime timeStamp, String licensePlate, Appointment appointment) {
        super(timeStamp, licensePlate);
        this.appointment = appointment;
    }
}
