package be.kdg.land.controller.dto.in;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class NewAppointmentDto {
    @NotBlank(message = "You must provide a customer name")
    private String customerName;

    @FutureOrPresent(message = "Only appointments for the future are allowed")
    private LocalDateTime slot;

    @NotBlank(message = "You must provide a rawMaterial")
    private String rawMaterial;

    @NotBlank (message = "You must provide the licenseplate of the delivery truck")
    private String licensePlate;

    public NewAppointmentDto() {
    }

    public NewAppointmentDto(String customerName, LocalDateTime slot, String rawMaterial, String licensePlate) {
        this.customerName = customerName;
        this.slot = slot;
        this.rawMaterial = rawMaterial;
        this.licensePlate = licensePlate;
    }
}
