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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PassageService {

    private final PayloadDeliveryService payloadDeliveryService;
    private final AppointmentService appointmentService;
    private final PassageRepository passageRepository;
    private final PayloadDeliveryRepository payloadDeliveryRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(PassageService.class);

    public PassageService(PayloadDeliveryService payloadDeliveryService, AppointmentService appointmentService, PassageRepository passageRepository, PayloadDeliveryRepository payloadDeliveryRepository) {
        this.payloadDeliveryService = payloadDeliveryService;
        this.appointmentService = appointmentService;
        this.passageRepository = passageRepository;
        this.payloadDeliveryRepository = payloadDeliveryRepository;
    }

    @Transactional
    public Optional<PayloadDelivery> enterFacility(String licensePlate, LocalDateTime arrivalTime) {

        Optional<Appointment> appointment = appointmentService.findValidAppointment(licensePlate, arrivalTime);
        if (appointment.isEmpty()) {
            throw new NoValidAppointmentException(licensePlate);
        }

        Entry entry = passageRepository.save(new Entry(arrivalTime, licensePlate, appointment.get()));

        Optional<PayloadDelivery> newPd = Optional.of(payloadDeliveryService.addPayloadDeliveryOnEntry(appointment.get().getCustomer(), appointment.get().getRawMaterial(), licensePlate, entry));

        LOGGER.info("The gate opened and {} was allowed IN.", licensePlate);

        return newPd;
    }

    @Transactional
    public void exitFacility(String licensePlate, LocalDateTime exitTime) {

        Optional<PayloadDelivery> payloadDeliveryOpt = payloadDeliveryRepository.findByLicensePlateAndExitIsNullAndEntryIsNotNull(licensePlate);
        if (payloadDeliveryOpt.isEmpty()) {
            throw new RuntimeException("Payload delivery for license Plate " + licensePlate + " not found");
        }

        PayloadDelivery payloadDelivery = payloadDeliveryOpt.get();
        Exit newExit = new Exit(exitTime, licensePlate);

        payloadDelivery.setExit(newExit);

        passageRepository.save(newExit);
        payloadDeliveryRepository.save(payloadDelivery);


        LOGGER.info("The gate opened and {} was allowed OUT.", licensePlate);
    }
}
