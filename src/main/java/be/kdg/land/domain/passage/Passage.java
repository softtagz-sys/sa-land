package be.kdg.land.domain.passage;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "passage_type")
@Table (name = "passages")
public class Passage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID passageId;

    @NotNull
    private LocalDateTime timeStamp;

    @NotBlank
    private String licensePlate;

    public Passage() {
    }

    public Passage(LocalDateTime timeStamp, String licensePlate) {
        this.timeStamp = timeStamp;
        this.licensePlate = licensePlate;
    }
}
