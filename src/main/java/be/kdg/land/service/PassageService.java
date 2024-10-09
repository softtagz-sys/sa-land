package be.kdg.land.service;

import be.kdg.land.LandApplicationConfig;
import be.kdg.land.domain.PayloadDelivery;
import be.kdg.land.domain.appointment.Appointment;
import be.kdg.land.domain.passage.Entry;
import be.kdg.land.domain.passage.Exit;
import be.kdg.land.domain.passage.Passage;
import be.kdg.land.repository.PassageRepository;
import be.kdg.land.service.exceptions.NoValidAppointmentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PassageService {

    @Autowired private PayloadDeliveryService payloadDeliveryService;
    @Autowired private AppointmentService appointmentService;

    @Autowired private PassageRepository passageRepository;

    @Autowired private LandApplicationConfig config;

    Logger logger = LoggerFactory.getLogger(PassageService.class);


    public String truckAtGate(String licensePlate, LocalDateTime arrivalTime) {
        List<Passage> passages = passageRepository.findAllByLicensePlate(licensePlate);

        if (passages.size() % 2 == 1) { // Truck is in facility
            return exitFacility(licensePlate, arrivalTime);
        } else {
            return enterFacility(licensePlate, arrivalTime);
        }
    }

    private String enterFacility(String licensePlate, LocalDateTime arrivalTime) {

        Optional<Appointment> appointment = appointmentService.findValidAppointment(licensePlate, arrivalTime);
        if (appointment.isEmpty()) {
            throw new NoValidAppointmentException(licensePlate);
        }

        // TODO check if validation is necessary
        Entry entry = passageRepository.save(new Entry(arrivalTime, licensePlate, appointment.get()));

        PayloadDelivery newPd = payloadDeliveryService.addPayloadDeliveryOnEntry(appointment.get().getCustomer(), appointment.get().getRawMaterial(), licensePlate, entry);

        logger.info(String.format("The gate opened and %s was allowed IN.", licensePlate));

        return String.format("You may enter the facility and proceed to weighbridge: %s", newPd.getEntryWeighing().getWeighBridge());
    }

    private String exitFacility(String licensePlate, LocalDateTime exitTime) {

        Exit newExit = new Exit(exitTime, licensePlate);
        // TODO check if validation is necessary
        passageRepository.save(newExit);
        logger.info(String.format("The gate opened and %s was allowed OUT.", licensePlate));
        return "Bye!";
    }
}
