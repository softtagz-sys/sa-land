package be.kdg.land.controller.dto.out;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class QueueItemDto {

    private LocalDateTime slot;
    private String licensePlate;

    public QueueItemDto(LocalDateTime slot, String licensePlate) {
        this.slot = slot;
        this.licensePlate = licensePlate;
    }
}
