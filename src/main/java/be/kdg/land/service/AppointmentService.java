package be.kdg.land.service;

import be.kdg.land.config.LandApplicationConfig;
import be.kdg.land.controller.TicketController;
import be.kdg.land.domain.RawMaterial;
import be.kdg.land.domain.Warehouse;
import be.kdg.land.domain.appointment.Appointment;
import be.kdg.land.domain.appointment.AppointmentType;
import be.kdg.land.domain.customer.Customer;
import be.kdg.land.messaging.WarehouseSender;
import be.kdg.land.messaging.dto.WarehouseStatusDto;
import be.kdg.land.repository.AppointmentRepository;
import be.kdg.land.repository.WarehouseRepository;
import be.kdg.land.repository.dto.AppointmentCountPerHour;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

@Service
public class AppointmentService {

    @Autowired private WarehouseSender warehouseSender;

    @Autowired private AppointmentRepository appointmentRepository;
    @Autowired private WarehouseRepository warehouseRepository;
    @Autowired private LandApplicationConfig config;

    private static final Logger LOGGER = Logger.getLogger(AppointmentService.class.getName());


    public Optional<Appointment> addAppointment(Customer customer, LocalDateTime slot, RawMaterial rawMaterial, String licensePlate) {

        // Validations
        validateMaxAmountOfAppointmentsPerSlot(slot);
        validateValidSlotBusinessHours(slot);

        boolean warehouseAvailable = checkIfWarehouseIsAvailable(customer, rawMaterial);
        if (!warehouseAvailable) {
            return Optional.empty();
        }

        Appointment appointment = new Appointment(customer, slot, rawMaterial, licensePlate, AppointmentType.APPOINTMENT);

        return Optional.of(this.appointmentRepository.save(appointment));

    }

    public Optional<Appointment> addToWaitingQueue(Customer customer, RawMaterial rawMaterial, String licensePlate, LocalDateTime simulatedTimeOfRegistration) {

        // Find First Free Slot
        LocalDateTime slot = findFirstFreeOutOfHoursSlot(simulatedTimeOfRegistration);

        Appointment appointment = new Appointment(customer, slot, rawMaterial, licensePlate, AppointmentType.WAITING_QUEUE);

        return Optional.of(this.appointmentRepository.save(appointment));

    }

    private LocalDateTime findFirstFreeOutOfHoursSlot(LocalDateTime simulatedTimeOfRegistration) {
        LocalDateTime firstFreeSlot = null;
        LocalDateTime firstPossibleSlot = simulatedTimeOfRegistration.truncatedTo(ChronoUnit.HOURS);
        List<AppointmentCountPerHour> slots = appointmentRepository.CountAppointmentsPerSlot(firstPossibleSlot);

        while(firstFreeSlot == null) {

            LocalDateTime currentTargetTime = firstPossibleSlot; // To circumvent lambda's need for final vars

            boolean afterAppointments = currentTargetTime.getHour() >= config.getAppointmentsEndHour();
            boolean beforeAppointments = currentTargetTime.getHour() < config.getAppointmentsStartHour();

            boolean slotFree = slots.stream()
                    .filter(slot -> slot.getSlot().equals(currentTargetTime))
                    .noneMatch(slot -> slot.getCount() >= config.getMaxAmountOfWaitingQueueAppointmentsPerSlot());

            if ((afterAppointments || beforeAppointments) && slotFree) {
                firstFreeSlot = currentTargetTime;
            }

            firstPossibleSlot = firstPossibleSlot.plusMinutes(config.getSlotDuration());
        }

        return firstFreeSlot;
    }

    private void validateMaxAmountOfAppointmentsPerSlot(LocalDateTime slot) {
        int size = appointmentRepository.findBySlot(slot).size();
        if (size >= config.getMaxAmountOfAppointmentsPerSlot()) {
            throw new IllegalStateException("Max amount of appointment per slot has been reached");
        }
    }

    private void validateValidSlotBusinessHours(LocalDateTime slotTime) {
        boolean withinTimeRange = slotTime.getHour() >= config.getAppointmentsStartHour() && slotTime.getHour() < config.getAppointmentsEndHour();
        boolean isOnFullHour = slotTime.getMinute() == 0;

        if (!(withinTimeRange && isOnFullHour)) {
            throw new IllegalStateException("Slot time must be within time range of business hours, and be on the hour");
        }
    }

    private boolean checkIfWarehouseIsAvailable(Customer customer, RawMaterial rawMaterial) {
        Optional<Warehouse> warehouseOptional = warehouseRepository.findByCustomer_CustomerIdAndRawMaterial_RawMaterialId(customer.getCustomerId(), rawMaterial.getRawMaterialId());
        if (warehouseOptional.isEmpty()) {
            return false;
        }

        try {
            WarehouseStatusDto warehouseStatusDto = warehouseSender.getWarehouseStatus(warehouseOptional.get().getWarehouseId().toString());
            return warehouseStatusDto.isAvailable();
        } catch (FeignException.NotFound e) {
            LOGGER.log(Level.WARNING, String.format("Failed to GET: %s",e.getMessage()));
            return false;
        }

    }

    public Optional<Appointment> findValidAppointment(String licensePlate, LocalDateTime arrivalTime) {

        List<Appointment> appointments = appointmentRepository.findByLicensePlate(licensePlate);

        return appointments.stream().filter(a -> isInSlot(a.getSlot(), arrivalTime)).findAny();
    }

    private boolean isInSlot(LocalDateTime slot, LocalDateTime arrivalTime) {
        return arrivalTime.isAfter(slot.minusSeconds(1)) && arrivalTime.isBefore(slot.plusMinutes(config.getSlotDuration()));
    }

}
