package be.kdg.land.controller.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
public class WaitingQueueAppointmentDto {

    @Setter
    @NotBlank(message = "You must provide a customer name")
    private String customerName;

    @Setter
    @NotBlank(message = "You must provide a rawMaterial")
    private String rawMaterial;

    @NotNull
    @Setter
    private LocalDateTime simulatedTimeOfRegistration;

    @NotBlank (message = "You must provide the licenseplate of the delivery truck")
    @Setter
    private String licensePlate;

    public WaitingQueueAppointmentDto() {
    }

    public WaitingQueueAppointmentDto(LocalDateTime simulatedTimeOfRegistration, String licensePlate) {
        this.simulatedTimeOfRegistration = simulatedTimeOfRegistration;
        this.licensePlate = licensePlate;
    }
}
