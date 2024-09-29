package be.kdg.land.domain;

import be.kdg.land.domain.customer.Customer;
import jakarta.persistence.*;
import jakarta.validation.Constraint;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.UUID;

@Getter
@Entity
@Table(name = "warehouses")

public class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID warehouseId;

    @ManyToOne
    @NotNull
    private Customer customer;

    @ManyToOne
    @NotNull
    private RawMaterial rawMaterial;

    public Warehouse() {
    }

}
