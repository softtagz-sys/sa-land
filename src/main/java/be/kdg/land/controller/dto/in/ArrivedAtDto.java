package be.kdg.land.controller.dto.in;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ArrivedAtDto {

    @NotNull(message = "You must provide the licensePlate")
    private String licensePlate;

    public ArrivedAtDto(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public ArrivedAtDto() {
    }
}
