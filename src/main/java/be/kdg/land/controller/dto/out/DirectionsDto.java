package be.kdg.land.controller.dto.out;

import lombok.Getter;

@Getter
public class DirectionsDto {

    private final String message;

    public DirectionsDto(String message) {
        this.message = message;
    }
}
