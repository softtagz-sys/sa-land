package be.kdg.land.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PassageDto {

    @NotBlank(message = "You must provide the licenseplate of the delivery truck")
    private String licensePlate;

    @NotNull
    private LocalDateTime arrivalTime;

    public PassageDto() {
    }

    public PassageDto(String licensePlate, LocalDateTime arrivalTime) {
        this.licensePlate = licensePlate;
        this.arrivalTime = arrivalTime;
    }
}
