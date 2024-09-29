package be.kdg.land.service;

import be.kdg.land.domain.appointment.Appointment;
import be.kdg.land.domain.passage.Exit;
import be.kdg.land.domain.passage.Passage;
import be.kdg.land.domain.passage.ScheduledEntry;
import be.kdg.land.domain.waitlist.TruckOnWaitlist;
import be.kdg.land.repository.AppointmentRepository;
import be.kdg.land.repository.PassageRepository;
import be.kdg.land.repository.WaitlistTruckRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class PassageService {
    private final AppointmentRepository appointmentRepository;
    private final PassageRepository passageRepository;
    private final WaitlistTruckRepository waitlistTruckRepository;

    Logger logger = LoggerFactory.getLogger(PassageService.class);

    @Value("${app.slotDuration}")
    private int slotDuration;

    @Value("${app.appointmentsStartHour}")
    private int appointmentsStartHour;

    @Value("${app.appointmentsEndHour}")
    private int appointmentsEndHour;

    public PassageService(AppointmentRepository appointmentRepository, PassageRepository passageRepository, WaitlistTruckRepository waitlistTruckRepository) {
        this.appointmentRepository = appointmentRepository;
        this.passageRepository = passageRepository;
        this.waitlistTruckRepository = waitlistTruckRepository;
    }

    public String truckAtGate(String licensePlate, LocalDateTime arrivalTime) {
        List<Passage> passages = passageRepository.findAllByLicensePlate(licensePlate);

        if (passages.size() % 2 == 1) { // Truck is in facility
            return Exit(licensePlate, arrivalTime);
        }
        else {
            return Entry(licensePlate, arrivalTime);
        }
    }

    private String Entry(String licensePlate, LocalDateTime arrivalTime) {
        if (arrivalTime.getHour() > appointmentsStartHour && arrivalTime.getHour() < appointmentsEndHour) {
            Optional<Appointment> appointment = FindValidAppointment(licensePlate, arrivalTime);
            if (appointment.isPresent()) {

                passageRepository.save(new ScheduledEntry(arrivalTime, licensePlate, appointment.get()));
                logger.info(String.format("The gate opened and %s was allowed IN.", licensePlate));

                return "You may enter the facility and proceed to the weighbridge.";
            }
        }

        return AddTruckToQueue(licensePlate, arrivalTime);
    }

    private String Exit(String licensePlate, LocalDateTime exitTime) {

        Exit newExit = new Exit(exitTime, licensePlate);

        passageRepository.save(newExit);
        logger.info(String.format("The gate opened and %s was allowed OUT.", licensePlate));
        return "Bye!";
    }


    private String AddTruckToQueue(String licensePlate, LocalDateTime arrivalTime) {
        TruckOnWaitlist newTruckOnWaitlist = new TruckOnWaitlist(licensePlate, arrivalTime);
        waitlistTruckRepository.save(newTruckOnWaitlist);

        return "You have been placed in the queue!";
    }

    private Optional<Appointment> FindValidAppointment(String licensePlate, LocalDateTime arrivalTime) {

        List<Appointment> appointments = this.appointmentRepository.findByLicensePlate(licensePlate);

        return appointments.stream().filter(a -> IsInSlot(a.getSlot(), arrivalTime)).findAny();

    }

    private boolean IsInSlot(LocalDateTime slot, LocalDateTime arrivalTime) {
        return arrivalTime.isAfter(slot.minusSeconds(1)) && arrivalTime.isBefore(slot.plusMinutes(slotDuration));
    }
}
