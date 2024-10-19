package be.kdg.land.controller.dto.in_out;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class WaitingQueueAppointmentDto {


    @NotBlank(message = "You must provide a customer name")
    private String customerName;

    @NotBlank(message = "You must provide a rawMaterial")
    private String rawMaterial;

    @NotNull
    private LocalDateTime simulatedTimeOfRegistration;

    @NotBlank (message = "You must provide the licenseplate of the delivery truck")
    private String licensePlate;

    public WaitingQueueAppointmentDto() {
    }

    public WaitingQueueAppointmentDto(LocalDateTime simulatedTimeOfRegistration, String licensePlate) {
        this.simulatedTimeOfRegistration = simulatedTimeOfRegistration;
        this.licensePlate = licensePlate;
    }
}