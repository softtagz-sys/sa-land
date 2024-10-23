package be.kdg.land.controller;


import be.kdg.land.controller.dto.in.NewAppointmentDto;
import be.kdg.land.controller.dto.in.NewWaitingQueueAppointmentDto;
import be.kdg.land.controller.dto.out.AppointmentDto;
import be.kdg.land.domain.RawMaterial;
import be.kdg.land.domain.appointment.Appointment;
import be.kdg.land.domain.customer.Customer;
import be.kdg.land.service.AppointmentService;
import be.kdg.land.service.CustomerService;
import be.kdg.land.service.RawMaterialService;
import be.kdg.land.service.exceptions.MaxAmountAppointmentsSlotReached;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

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

    @GetMapping("/appointment-form")
    public String appointmentForm(ModelMap model) {
        model.addAttribute("new_appointment", new NewAppointmentDto());
        return "appointments/appointmentForm";
    }

    @PostMapping("/appointment")
    public ModelAndView newAppointment(@ModelAttribute("new_appointment") @Valid NewAppointmentDto newAppointment, BindingResult errors) {
        ModelAndView modelAndView = new ModelAndView();

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
                modelAndView.getModelMap().addAttribute("appointmentLogicError", "Warehouse is not available for delivery");
                modelAndView.setViewName("appointments/appointmentForm");
            }

        } catch (ConstraintViolationException | MaxAmountAppointmentsSlotReached e) {
            modelAndView.getModelMap().addAttribute("appointmentLogicError", e.getMessage());
            modelAndView.setViewName("appointments/appointmentForm");
        }

        return modelAndView;
    }

    @GetMapping("/waitingqueue-form")
    public String waitingQueueForm(ModelMap model) {

        model.addAttribute("new_waitingqueueappointment", new NewWaitingQueueAppointmentDto());
        return "appointments/waitingQueueAppointmentForm";
    }

    @PostMapping("/waitingqueue")
    public ModelAndView addToWaitingQueue(@ModelAttribute("new_waitingqueueappointment") @Valid NewWaitingQueueAppointmentDto waitingQueueAppointmentDto, BindingResult errors) {
        ModelAndView modelAndView = new ModelAndView();

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
                modelAndView.getModelMap().addAttribute("appointmentLogicError", "Warehouse is not available for delivery");
                modelAndView.setViewName("appointments/waitingQueueAppointmentForm");
            }

        } catch (ConstraintViolationException e) {
            modelAndView.getModelMap().addAttribute("appointmentLogicError", e.getMessage());
            modelAndView.setViewName("appointments/waitingQueueAppointmentForm");
        }

        return modelAndView;
    }

}
