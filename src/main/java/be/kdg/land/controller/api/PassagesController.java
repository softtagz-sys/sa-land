package be.kdg.land.controller.api;


import be.kdg.land.controller.dto.GateMessageDto;
import be.kdg.land.controller.dto.PassageDto;
import be.kdg.land.service.AppointmentService;
import be.kdg.land.service.PassageService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/passages")
public class PassagesController {

    private final PassageService passageService;

    public PassagesController(PassageService passageService) {
        this.passageService = passageService;
    }

    @PostMapping()
    public ResponseEntity<GateMessageDto> addPassage(@RequestBody @Valid PassageDto passage) {

        String message = passageService.truckAtGate(passage.getLicensePlate(), passage.getArrivalTime());

        return ResponseEntity.status(HttpStatus.CREATED).body(new GateMessageDto(message));
    }
}
