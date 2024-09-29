package be.kdg.land.controller;

import be.kdg.land.controller.dto.AppointmentDto;
import be.kdg.land.domain.RawMaterial;
import be.kdg.land.domain.customer.Customer;
import be.kdg.land.service.AppointmentService;
import be.kdg.land.service.CustomerService;
import be.kdg.land.service.RawMaterialService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class AppointmentController {
    private final AppointmentService appointmentService;
    private final CustomerService customerService;
    private final RawMaterialService rawMaterialService;

    public AppointmentController(AppointmentService appointmentService, CustomerService customerService, RawMaterialService rawMaterialService) {
        this.appointmentService = appointmentService;
        this.customerService = customerService;
        this.rawMaterialService = rawMaterialService;
    }

    @GetMapping("/add-appointment")
    public String addAppointment(ModelMap model) {
        model.addAttribute("new_appointment", new AppointmentDto());
        return "appointments/appointmentForm";
    }

    @PostMapping("/add-appointment")
    public String addAppointment(@ModelAttribute("new_appointment") @Valid AppointmentDto newAppointment, BindingResult errors, ModelMap model) {

        Optional<Customer> customer = customerService.getCustomerByName(newAppointment.getCustomerName());
        Optional<RawMaterial> rawMaterial = rawMaterialService.findRawMaterialByName(newAppointment.getRawMaterial());

        if (customer.isEmpty()) {
            model.addAttribute("customerNotFound", "Customer not found");
        }

        if (rawMaterial.isEmpty()) {
            model.addAttribute("rawMaterialNotFound", "Raw material not found");
        }

        if(errors.hasErrors() | customer.isEmpty() | rawMaterial.isEmpty() ) {
            return "appointments/appointmentForm";
        }

        try {
            appointmentService.add(customer.get(), newAppointment.getSlot(), rawMaterial.get(), newAppointment.getLicensePlate());
        } catch (ConstraintViolationException | IllegalStateException e) {
            model.addAttribute("appointmentLogicError", e.getMessage());
            return "appointments/appointmentForm";
        }

        return "redirect:/";
    }


}
