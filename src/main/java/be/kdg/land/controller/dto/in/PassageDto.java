package be.kdg.land.controller.dto.in;

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

    private boolean incoming;

    public PassageDto() {
    }

    public PassageDto(String licensePlate, LocalDateTime arrivalTime, boolean incoming) {
        this.licensePlate = licensePlate;
        this.arrivalTime = arrivalTime;
        this.incoming = incoming;
    }
}
