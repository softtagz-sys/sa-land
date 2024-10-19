package be.kdg.land.messaging.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class DeliveryCompleteDto {

    private String customerId;
    private String rawMaterial;
    private double weight;
    private LocalDateTime timestamp;

    public DeliveryCompleteDto(String customerId, String rawMaterial, double weight, LocalDateTime timestamp) {
        this.customerId = customerId;
        this.rawMaterial = rawMaterial;
        this.weight = weight;
        this.timestamp = timestamp;
    }
}
