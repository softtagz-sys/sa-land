package be.kdg.land.domain.appointment;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;


public class Slot {

    @NotNull(message = "You must provide a date")
    private final LocalDate slotDate;

    @Min(value = 8, message = "The earliest time for an appointment is 08:00")
    @Max(value = 20, message = "The latest time for an appointment is 20:00")
    private final int slotHour;

    public Slot(LocalDate slotDate, int slotHour) {
        this.slotDate = slotDate;
        this.slotHour = slotHour;
    }
}
