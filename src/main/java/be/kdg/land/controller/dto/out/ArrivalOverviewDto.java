package be.kdg.land.controller.dto.out;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ArrivalOverviewDto {

    private final LocalDateTime slot;
    private final String licensePlate;
    private final boolean arrived;

    public ArrivalOverviewDto(LocalDateTime slot, String licensePlate, boolean arrived) {
        this.slot = slot;
        this.licensePlate = licensePlate;
        this.arrived = arrived;
    }
}
