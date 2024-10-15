package be.kdg.land.service;

import be.kdg.land.domain.PayloadDelivery;
import be.kdg.land.domain.appointment.Appointment;
import be.kdg.land.domain.passage.Entry;
import be.kdg.land.domain.passage.Exit;
import be.kdg.land.repository.PassageRepository;
import be.kdg.land.repository.PayloadDeliveryRepository;
import be.kdg.land.service.exceptions.NoValidAppointmentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PassageService {

    @Autowired private PayloadDeliveryService payloadDeliveryService;
    @Autowired private AppointmentService appointmentService;

    @Autowired private PassageRepository passageRepository;
    @Autowired private PayloadDeliveryRepository payloadDeliveryRepository;

    Logger logger = LoggerFactory.getLogger(PassageService.class);



    public Optional<PayloadDelivery> enterFacility(String licensePlate, LocalDateTime arrivalTime) {

        Optional<Appointment> appointment = appointmentService.findValidAppointment(licensePlate, arrivalTime);
        if (appointment.isEmpty()) {
            throw new NoValidAppointmentException(licensePlate);
        }

        Entry entry = passageRepository.save(new Entry(arrivalTime, licensePlate, appointment.get()));

        Optional<PayloadDelivery> newPd = Optional.of(payloadDeliveryService.addPayloadDeliveryOnEntry(appointment.get().getCustomer(), appointment.get().getRawMaterial(), licensePlate, entry));

        logger.info(String.format("The gate opened and %s was allowed IN.", licensePlate));

        return newPd;
    }

    public void exitFacility(String licensePlate, LocalDateTime exitTime) {

        Optional<PayloadDelivery> payloadDeliveryOpt = payloadDeliveryRepository.findByLicensePlateAndExitIsNullAndEntryIsNotNull(licensePlate);
        if (payloadDeliveryOpt.isEmpty()) {
            throw new RuntimeException("Payload delivery for license Plate " + licensePlate + " not found");
        }

        PayloadDelivery payloadDelivery = payloadDeliveryOpt.get();
        Exit newExit = new Exit(exitTime, licensePlate);

        payloadDelivery.setExit(newExit);

        passageRepository.save(newExit);
        payloadDeliveryRepository.save(payloadDelivery); //Todo check if this is necessary?


        logger.info(String.format("The gate opened and %s was allowed OUT.", licensePlate));
    }
}
