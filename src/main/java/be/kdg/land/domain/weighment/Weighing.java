package be.kdg.land.domain.weighment;

import be.kdg.land.domain.PayloadDelivery;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@Table(name = "weighings")
public class Weighing {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID weighingId;

    @Setter
    private LocalDateTime timestamp;

    @Setter
    private double weight;

    private String weighBridge;

    private String LicensePlate;

    @ManyToOne()
    private PayloadDelivery payloadDelivery;

    public Weighing() {
    }

    public Weighing(String weighBridge, String licensePlate) {
        this.weighBridge = weighBridge;
        LicensePlate = licensePlate;
    }
}
