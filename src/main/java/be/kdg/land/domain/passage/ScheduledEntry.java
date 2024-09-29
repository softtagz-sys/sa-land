package be.kdg.land.domain.passage;


import be.kdg.land.domain.appointment.Appointment;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

//@NoArgsConstructor
@RequiredArgsConstructor
@Entity
@DiscriminatorValue("SCHEDULED_ENTRY")
public class ScheduledEntry extends Entry {

    @NotNull(message = "You can only enter if you provide an appointment")
    @OneToOne
    private Appointment appointment;

}
