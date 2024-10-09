package be.kdg.land.domain.appointment;

import be.kdg.land.domain.RawMaterial;
import be.kdg.land.domain.customer.Customer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;


import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID appointmentId;

    @ManyToOne
    @NotNull
    private Customer customer;


    @NotNull
    private LocalDateTime slot;

    @ManyToOne
    @NotNull
    private RawMaterial rawMaterial;

    @NotBlank
    private String licensePlate;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AppointmentType appointmentType;


    public Appointment() {
    }

    public Appointment(Customer customer, LocalDateTime slot, RawMaterial rawMaterial, String licensePlate, AppointmentType appointmentType) {
        this.customer = customer;
        this.slot = slot;
        this.rawMaterial = rawMaterial;
        this.licensePlate = licensePlate;
        this.appointmentType = appointmentType;
    }
}
