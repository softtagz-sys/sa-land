package be.kdg.land.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Getter
@Entity()
@Table(name = "raw_materials")
public class RawMaterial {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID rawMaterialId;

    @NotBlank(message = "A raw material type must have a name")
    @Column(unique = true)
    private String name;

    @NotBlank(message = "A raw material type must have a description")
    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "rawMaterial", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Warehouse> warehouses;

    public RawMaterial() {
    }

    public RawMaterial(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
