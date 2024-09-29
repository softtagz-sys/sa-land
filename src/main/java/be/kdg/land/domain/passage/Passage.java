package be.kdg.land.domain.passage;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
//@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "passage_type")
//Entity@Table (name = "passages")
public class Passage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID passageId;

    @NotNull(message = "A passage must have a timestamp")
    private LocalDateTime timeStamp;

    @NotBlank(message = "A passage must have a licenseplate")
    private String licensePlate;

}
