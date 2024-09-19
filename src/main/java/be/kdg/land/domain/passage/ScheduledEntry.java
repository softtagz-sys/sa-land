package be.kdg.land.domain.passage;

import be.kdg.land.domain.Truck;
import be.kdg.land.domain.appointment.Appointment;

import java.time.LocalDateTime;

public class ScheduledEntry extends Entry {

    private final Appointment appointment;

    public ScheduledEntry(LocalDateTime timeStamp, Gate gate, Truck truck, Appointment appointment) {
        super(timeStamp, gate, truck);
        this.appointment = appointment;
    }
}
