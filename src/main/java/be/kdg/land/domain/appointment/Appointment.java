package be.kdg.land.domain.appointment;

import be.kdg.land.domain.RawMaterial;
import be.kdg.land.domain.customer.Customer;
import be.kdg.land.domain.validators.ValidSlot;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID appointmentId;

    @ManyToOne
    @NotNull(message = "An appointment must be assigned to a customer.")
    private Customer customer;

    @ValidSlot
    @NotNull(message = "You must provide a appointment time.")
    private LocalDateTime slot;

    @ManyToOne
    @NotNull(message = "You must provide a raw material type.")
    private RawMaterial rawMaterial;

    @NotBlank(message = "A license plate must be provided to gain access to the facility")
    private String licensePlate;

    public Appointment(Customer customer, LocalDateTime slot, RawMaterial rawMaterial, String licensePlate) {
        this.customer = customer;
        this.slot = slot;
        this.rawMaterial = rawMaterial;
        this.licensePlate = licensePlate;
    }
}
