package be.kdg.land.controller;

import be.kdg.land.controller.dto.AppointmentDto;
import be.kdg.land.controller.dto.WaitingQueueAppointmentDto;
import be.kdg.land.domain.RawMaterial;
import be.kdg.land.domain.appointment.Appointment;
import be.kdg.land.domain.customer.Customer;
import be.kdg.land.service.AppointmentService;
import be.kdg.land.service.CustomerService;
import be.kdg.land.service.RawMaterialService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.Optional;

@Controller
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private RawMaterialService rawMaterialService;


    @GetMapping("/add-appointment")
    public String addAppointment(ModelMap model) {
        model.addAttribute("new_appointment", new AppointmentDto());
        return "appointments/appointmentForm";
    }

    @PostMapping("/add-appointment")
    public ModelAndView addAppointment(@ModelAttribute("new_appointment") @Valid AppointmentDto newAppointment, BindingResult errors) {
        final ModelAndView modelAndView = new ModelAndView();

        Optional<Customer> customer = customerService.getCustomerByName(newAppointment.getCustomerName());
        Optional<RawMaterial> rawMaterial = rawMaterialService.findRawMaterialByName(newAppointment.getRawMaterial());

        if (customer.isEmpty()) {
            modelAndView.getModelMap().addAttribute("customerNotFound", "Customer not found");
        }

        if (rawMaterial.isEmpty()) {
            modelAndView.getModelMap().addAttribute("rawMaterialNotFound", "Raw material not found");
        }

        if (errors.hasErrors() | customer.isEmpty() | rawMaterial.isEmpty()) {
            modelAndView.setViewName("appointments/appointmentForm");
            return modelAndView;
        }

        try {
            Optional<Appointment> appointment = appointmentService.addAppointment(customer.get(), newAppointment.getSlot(), rawMaterial.get(), newAppointment.getLicensePlate());
            if (appointment.isPresent()) {
                modelAndView.setViewName("appointments/confirmedAppointment");
                Appointment app = appointment.get();
                modelAndView.getModelMap().addAttribute("confirmedAppointment", new AppointmentDto(
                        app.getCustomer().getName(), app.getSlot(), app.getRawMaterial().getName(), app.getLicensePlate()));
                modelAndView.getModelMap().addAttribute("appointmentType", "Appointment");

            } else {
                modelAndView.setViewName("appointments/waitingQueueAppointmentForm");
            }

        } catch (ConstraintViolationException | IllegalStateException e) {
            modelAndView.getModelMap().addAttribute("appointmentLogicError", e.getMessage());
            modelAndView.setViewName("appointments/appointmentForm");
        }

        return modelAndView;
    }

    @GetMapping("/add-to-waitingqueue/{licensePlate}/{simulatedTimeOfRegistration}")
    public String addToWaitingQueue(ModelMap model, @PathVariable String licensePlate, @PathVariable LocalDateTime simulatedTimeOfRegistration) {

        WaitingQueueAppointmentDto appointmentDto = new WaitingQueueAppointmentDto();
        appointmentDto.setLicensePlate(licensePlate);
        appointmentDto.setSimulatedTimeOfRegistration(simulatedTimeOfRegistration);

        // Add the DTO to the model
        model.addAttribute("new_waitingqueueappointment", appointmentDto);
//        model.addAttribute("new_waitingqueueappointment", new WaitingQueueAppointmentDto(simulatedTimeOfRegistration, licensePlate));
        return "appointments/waitingQueueAppointmentForm";
    }

    @PostMapping("/add-to-waitingqueue")
    public ModelAndView addToWaitingQueue(@ModelAttribute("new_waitingqueueappointment") @Valid WaitingQueueAppointmentDto waitingQueueAppointmentDto, BindingResult errors) {
        final ModelAndView modelAndView = new ModelAndView();


        Optional<Customer> customer = customerService.getCustomerByName(waitingQueueAppointmentDto.getCustomerName());
        Optional<RawMaterial> rawMaterial = rawMaterialService.findRawMaterialByName(waitingQueueAppointmentDto.getRawMaterial());

        if (customer.isEmpty()) {
            modelAndView.getModelMap().addAttribute("customerNotFound", "Customer not found");
        }

        if (rawMaterial.isEmpty()) {
            modelAndView.getModelMap().addAttribute("rawMaterialNotFound", "Raw material not found");
        }

        if (errors.hasErrors() | customer.isEmpty() | rawMaterial.isEmpty()) {
            modelAndView.setViewName("appointments/waitingQueueAppointmentForm");
            return modelAndView;
        }

        try {
            Optional<Appointment> appointment = appointmentService.addToWaitingQueue(customer.get(), rawMaterial.get(), waitingQueueAppointmentDto.getLicensePlate(), waitingQueueAppointmentDto.getSimulatedTimeOfRegistration());
            if (appointment.isPresent()) {
                modelAndView.setViewName("appointments/confirmedAppointment");
                Appointment app = appointment.get();
                modelAndView.getModelMap().addAttribute("confirmedAppointment", new AppointmentDto(
                        app.getCustomer().getName(), app.getSlot(), app.getRawMaterial().getName(), app.getLicensePlate()));
                modelAndView.getModelMap().addAttribute("appointmentType", "Waiting Queue");

            } else {
                modelAndView.setViewName("appointments/waitingQueueAppointmentForm");
            }

        } catch (ConstraintViolationException | IllegalStateException e) {
            modelAndView.getModelMap().addAttribute("appointmentLogicError", e.getMessage());
            modelAndView.setViewName("appointments/waitingQueueAppointmentForm");
        }

        return modelAndView;
    }

}
