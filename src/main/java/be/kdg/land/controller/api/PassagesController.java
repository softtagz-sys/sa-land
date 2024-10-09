package be.kdg.land.controller.api;


import be.kdg.land.controller.dto.GateMessageDto;
import be.kdg.land.controller.dto.PassageDto;
import be.kdg.land.service.AppointmentService;
import be.kdg.land.service.PassageService;
import be.kdg.land.service.exceptions.NoValidAppointmentException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/passages")
public class PassagesController {

    @Autowired
    private PassageService passageService;


    @PostMapping()
    public ResponseEntity<GateMessageDto> addPassage(@RequestBody @Valid PassageDto passage) {

        try {
            String message = passageService.truckAtGate(passage.getLicensePlate(), passage.getArrivalTime());

            return ResponseEntity.status(HttpStatus.CREATED).body(new GateMessageDto(message));
        } catch (NoValidAppointmentException e) {
            URI redirectUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path(String.format("/add-to-waitingqueue/%s/%s", passage.getLicensePlate(), passage.getArrivalTime()))
                    .build()
                    .toUri();

            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(redirectUri);

            GateMessageDto gateMessageDto = new GateMessageDto("You don't have a valid appointment for this timeslot. Please fill out your details so you can be placed in the waiting queue.");

            return ResponseEntity.status(HttpStatus.SEE_OTHER).headers(headers).body(gateMessageDto) ;
        }
    }
}
