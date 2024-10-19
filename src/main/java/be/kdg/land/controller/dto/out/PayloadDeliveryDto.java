package be.kdg.land.controller.dto.out;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PayloadDeliveryDto {

    private final String payloadDeliveryId;
    private final String customerName;
    private final LocalDateTime deliveryTime;
    private final boolean wbtAvailable;
    private final boolean pdtAvailable;

    public PayloadDeliveryDto(String payloadDeliveryId, String customerName, LocalDateTime deliveryTime, boolean wbtAvailable, boolean pdtAvailable) {
        this.payloadDeliveryId = payloadDeliveryId;
        this.customerName = customerName;
        this.deliveryTime = deliveryTime;
        this.wbtAvailable = wbtAvailable;
        this.pdtAvailable = pdtAvailable;
    }
}
