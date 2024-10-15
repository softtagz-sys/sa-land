package be.kdg.land.controller.dto.out;

import lombok.Getter;

@Getter
public class DirectionsDto {

    private String message;

    public DirectionsDto(String message) {
        this.message = message;
    }
}
