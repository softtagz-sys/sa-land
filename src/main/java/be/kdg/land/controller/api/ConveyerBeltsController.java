package be.kdg.land.controller.api;

import be.kdg.land.controller.dto.in.ArrivedAtDto;
import be.kdg.land.domain.PayloadDelivery;
import be.kdg.land.service.PayloadDeliveryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/conveyerbelts")
public class ConveyerBeltsController {

    @Autowired
    PayloadDeliveryService payloadDeliveryService;

    @PostMapping
    public ResponseEntity<Void> addConveyerBeltDelivery(@RequestBody @Valid ArrivedAtDto arrivedAtDto) {

        Optional<PayloadDelivery> payloadDeliveryOptional = payloadDeliveryService.addNewExitWeighing(arrivedAtDto.getLicensePlate());

        if (payloadDeliveryOptional.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}