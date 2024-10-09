package be.kdg.land.controller.dto;

import lombok.Getter;

public class GateMessageDto {

    @Getter private String message;

    public GateMessageDto(String message) {
        this.message = message;
    }
}
