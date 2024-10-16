package be.kdg.land.controller.dto.out;

import lombok.Getter;

@Getter
public class TruckPresentDto {
    private String licensePlate;
    private String customer;

    public TruckPresentDto(String licensePlate, String customer) {
        this.licensePlate = licensePlate;
        this.customer = customer;
    }
}
