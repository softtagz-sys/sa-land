package be.kdg.land.repository.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AppointmentCountPerHour {
    private LocalDateTime slot;
    private Long count;

    public AppointmentCountPerHour(LocalDateTime slot, Long count) {
        this.slot = slot;
        this.count = count;
    }

    public AppointmentCountPerHour() {
    }
}
