package be.kdg.land.domain.passage;

import be.kdg.land.domain.appointment.Appointment;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("ENTRY")
public class Entry extends Passage {


    @NotNull
    @OneToOne
    private Appointment appointment;

    public Entry() {
        super();
    }

    public Entry(LocalDateTime timeStamp, String licensePlate, Appointment appointment) {
        super(timeStamp, licensePlate);
        this.appointment = appointment;
    }
}
