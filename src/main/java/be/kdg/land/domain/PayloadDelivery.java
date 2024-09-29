package be.kdg.land.domain;

import be.kdg.land.domain.customer.Customer;
import be.kdg.land.domain.passage.Entry;
import be.kdg.land.domain.passage.Exit;
import be.kdg.land.domain.weighment.Weighing;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;



@Getter
@Entity
@Table(name = "payload_deliveries")
public class PayloadDelivery
{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID payloadDeliveryId;

    @NotNull
    @OneToOne
    private Customer customer;

    @NotNull
    @OneToOne
    private RawMaterial rawMaterial;

    @NotNull
    private String licensePlate;

    @NotNull
    @OneToOne
    private Entry entry;

    @OneToOne
    @Setter
    private Weighing entryWeighing;

    @OneToOne
    @Setter
    private Weighing exitWeighing;

    @OneToOne
    @Setter
    private Warehouse warehouse;

    @OneToOne
    @Setter
    private Exit exit;

    public PayloadDelivery() {
    }

    public PayloadDelivery(Customer customer, RawMaterial rawMaterial, String licensePlate, Entry entry) {
        this.customer = customer;
        this.rawMaterial = rawMaterial;
        this.licensePlate = licensePlate;
        this.entry = entry;
    }
}
