package be.kdg.land.controller.dto.tickets;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class WeighBridgeTicket {

    private final UUID payloadDeliveryId;
    private final Double grossWeightAtArrival;
    private final Double tareWeight;
    private final Double netWeight;
    private final LocalDateTime timestamp;
    private final String licensePlate;

    public WeighBridgeTicket(UUID payloadDeliveryId, Double grossWeightAtArrival, Double tareWeight, Double netWeight, LocalDateTime timestamp, String licensePlate) {
        this.payloadDeliveryId = payloadDeliveryId;
        this.grossWeightAtArrival = grossWeightAtArrival;
        this.tareWeight = tareWeight;
        this.netWeight = netWeight;
        this.timestamp = timestamp;
        this.licensePlate = licensePlate;
    }
}
