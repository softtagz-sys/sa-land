package be.kdg.land.controller.dto.tickets;

import be.kdg.land.domain.RawMaterial;
import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
public class PayloadDeliveryTicket {

    private final UUID payloadDeliveryTicketId;
    private final UUID warehouseId;
    private final String rawMaterial;
    private final LocalDate deliveryTime;

    public PayloadDeliveryTicket(UUID payloadDeliveryTicketId, UUID warehouseId, String rawMaterial, LocalDate deliveryTime) {
        this.payloadDeliveryTicketId = payloadDeliveryTicketId;
        this.warehouseId = warehouseId;
        this.rawMaterial = rawMaterial;
        this.deliveryTime = deliveryTime;
    }
}
