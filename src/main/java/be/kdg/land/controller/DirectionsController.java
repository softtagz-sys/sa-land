package be.kdg.land.controller;

import be.kdg.land.controller.dto.out.DirectionsDto;
import be.kdg.land.service.PayloadDeliveryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DirectionsController {


    private final PayloadDeliveryService payloadDeliveryService;

    public DirectionsController(PayloadDeliveryService payloadDeliveryService) {
        this.payloadDeliveryService = payloadDeliveryService;
    }

    @GetMapping("/directions")
    public String getLicensePlateData(@RequestParam(value = "licensePlate", required = false) String licensePlate, ModelMap model) {
        if (licensePlate != null && !licensePlate.isEmpty()) {

            String message = payloadDeliveryService.getDirections(licensePlate);

            model.addAttribute("directionsdata", new DirectionsDto(message));
        }

        return "directions/directions";
    }
}
