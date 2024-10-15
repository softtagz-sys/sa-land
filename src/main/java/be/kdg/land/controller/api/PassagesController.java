package be.kdg.land.controller.api;


import be.kdg.land.controller.dto.in.PassageDto;
import be.kdg.land.domain.PayloadDelivery;
import be.kdg.land.service.PassageService;
import be.kdg.land.service.exceptions.NoValidAppointmentException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/passages")
public class PassagesController {

    @Autowired
    private PassageService passageService;


    @PostMapping()
    public ResponseEntity<Void> addPassage(@RequestBody @Valid PassageDto passage) {

        if (passage.isIncoming()) {
            try {
                Optional<PayloadDelivery> payloadDelivery = passageService.enterFacility(passage.getLicensePlate(), passage.getArrivalTime());
                if (payloadDelivery.isEmpty()) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Appointment not found
                }

                return ResponseEntity.status(HttpStatus.CREATED).build();

            } catch (NoValidAppointmentException e) {
                return ResponseEntity.status(HttpStatus.SEE_OTHER).build();
            }

        } else {
            passageService.exitFacility(passage.getLicensePlate(), passage.getArrivalTime());

            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
    }
}
