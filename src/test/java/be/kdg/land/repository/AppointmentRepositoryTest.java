package be.kdg.land.repository;

import be.kdg.land.domain.appointment.Appointment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AppointmentRepositoryTest {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Test
    void canInsertNewValidAppointment() {


//        Appointment appointment = new Appointment("");
    }

//    @Test
//    void findBySlot() {
//    }
}