package be.kdg.land.service;

import be.kdg.land.domain.RawMaterial;
import be.kdg.land.domain.appointment.Appointment;
import be.kdg.land.domain.customer.Customer;
import be.kdg.land.repository.AppointmentRepository;
import jakarta.validation.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final Validator validator;

    @Value("${app.maxAmountOfAppointmentsPerSlot}")
    private int maxAmountOfAppointmentsPerSlot;


    public AppointmentService(AppointmentRepository appointmentRepository, Validator validator) {
        this.appointmentRepository = appointmentRepository;
        this.validator = validator;
    }

    public Optional<Appointment> add(Customer customer, LocalDateTime slot, RawMaterial rawMaterial, String licensePlate) {

        // Validations
        // TODO Validate max 1 appointment per slot per license plate
        validateMaxAmountOfAppointmentsPerSlot(slot);

        Appointment appointment = new Appointment(customer, slot, rawMaterial, licensePlate);
        Set<ConstraintViolation<Appointment>> violations = validator.validate(appointment);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        try {
            return Optional.of(this.appointmentRepository.save(appointment));
        } catch (DataIntegrityViolationException | TransactionSystemException e) {
            return Optional.empty();
        }
    }

    private void validateMaxAmountOfAppointmentsPerSlot(LocalDateTime slot) {

        int size = appointmentRepository.findBySlot(slot).size();
        if (size >= maxAmountOfAppointmentsPerSlot) {
            throw new IllegalStateException("Max amount of appointment per slot has been reached");
        }
    }




}
