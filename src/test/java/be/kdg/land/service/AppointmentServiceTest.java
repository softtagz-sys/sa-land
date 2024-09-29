package be.kdg.land.service;

import be.kdg.land.domain.RawMaterial;
import be.kdg.land.domain.appointment.Appointment;
import be.kdg.land.domain.customer.Customer;
import be.kdg.land.repository.AppointmentRepository;
import be.kdg.land.repository.CustomerRepository;
import be.kdg.land.repository.RawMaterialRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AppointmentServiceTest {

    @Autowired private AppointmentService appointmentService;

    @Autowired private AppointmentRepository appointmentRepository;

    @Autowired private CustomerRepository customerRepository;

    @Autowired private RawMaterialRepository rawMaterialRepository;


    @Test
    public void testAddValidAppointment() {

        Customer customer = customerRepository.getReferenceById(UUID.fromString("9ae35800-5fdf-4932-a713-251f49e11012"));
        RawMaterial rawMaterial = rawMaterialRepository.getReferenceById(UUID.fromString("59c1088e-df7d-4a6e-be9d-352d1d50ec44"));
        LocalDateTime slot = LocalDateTime.of(2024, 10, 1, 9, 0);
        String licensePlate = "1-AAA-001";

        Optional<Appointment> result = appointmentService.add(customer, slot, rawMaterial, licensePlate);

        // Assert that the appointment was saved successfully
        assertTrue(result.isPresent());
        assertNotNull(result.get().getAppointmentId());

        // Verify the appointment is actually in the database
        Optional<Appointment> savedAppointment = appointmentRepository.findById(result.get().getAppointmentId());
        assertTrue(savedAppointment.isPresent());
    }

}