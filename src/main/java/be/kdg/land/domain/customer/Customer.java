package be.kdg.land.domain.customer;

import be.kdg.land.domain.Warehouse;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;
import java.util.UUID;


@Getter
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID customerId;

    @Column(unique = true)
    @NotBlank
    private String name;

    @NotBlank
    private String address;

    @OneToMany(mappedBy = "customer")
    private List<Warehouse> warehouses;

    public Customer() {
    }

    public Customer(String name, String address) {
        this.name = name;
        this.address = address;
    }
}
