package be.kdg.land.controller.dto.in;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class WeighingOperationDto {

    @NotEmpty
    private final String licensePlate;

    @NotNull
    private final LocalDateTime timestamp;
    
    private final double weight;

    public WeighingOperationDto(String licensePlate, LocalDateTime timestamp, double weight) {
        this.licensePlate = licensePlate;
        this.timestamp = timestamp;
        this.weight = weight;
    }
}
