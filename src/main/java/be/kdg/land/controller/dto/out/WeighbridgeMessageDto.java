package be.kdg.land.controller.dto.out;

import be.kdg.land.controller.dto.tickets.WeighBridgeTicket;
import lombok.Getter;

import java.util.UUID;

@Getter
public class WeighbridgeMessageDto {
    private String message;
    private UUID warehouseId;
    private WeighBridgeTicket ticket;

    public WeighbridgeMessageDto(String message, UUID warehouseId, WeighBridgeTicket ticket) {
        this.message = message;
        this.warehouseId = warehouseId;
        this.ticket = ticket;
    }
}
