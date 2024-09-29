package be.kdg.land.domain.waitlist;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@Table(name = "waiting_queue")
public class TruckOnWaitlist {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID waitlistId;

    @NotNull
    @Column(unique = true)
    private String LicensePlate;

    @NotNull
    private LocalDateTime ArrivalTime;

    public TruckOnWaitlist() {
    }

    public TruckOnWaitlist(String licensePlate, LocalDateTime arrivalTime) {
        LicensePlate = licensePlate;
        ArrivalTime = arrivalTime;
    }
}
