package be.kdg.land.domain.weighment;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class WeighBridge {

    @NotBlank
    private final String location;

    public WeighBridge(String location) {
        this.location = location;
    }
}
