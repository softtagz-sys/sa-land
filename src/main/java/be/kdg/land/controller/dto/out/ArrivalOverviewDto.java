package be.kdg.land.controller.dto.out;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ArrivalOverviewDto {

    private LocalDateTime slot;
    private String licensePlate;
    private boolean arrived;

    public ArrivalOverviewDto(LocalDateTime slot, String licensePlate, boolean arrived) {
        this.slot = slot;
        this.licensePlate = licensePlate;
        this.arrived = arrived;
    }
}
