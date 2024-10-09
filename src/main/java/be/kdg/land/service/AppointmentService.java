package be.kdg.land.service;

import be.kdg.land.LandApplicationConfig;
import be.kdg.land.domain.RawMaterial;
import be.kdg.land.domain.appointment.Appointment;
import be.kdg.land.domain.appointment.AppointmentType;
import be.kdg.land.domain.customer.Customer;
import be.kdg.land.repository.AppointmentRepository;
import be.kdg.land.repository.dto.AppointmentCountPerHour;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    @Autowired private AppointmentRepository appointmentRepository;
    @Autowired private LandApplicationConfig config;


    public Optional<Appointment> addAppointment(Customer customer, LocalDateTime slot, RawMaterial rawMaterial, String licensePlate) {

        // Validations
        validateMaxAmountOfAppointmentsPerSlot(slot);
        validateValidSlotBusinessHours(slot);

        Appointment appointment = new Appointment(customer, slot, rawMaterial, licensePlate, AppointmentType.APPOINTMENT);

        try {
            return Optional.of(this.appointmentRepository.save(appointment));
        } catch (DataIntegrityViolationException e) {
            return Optional.empty();
        }
    }

    public Optional<Appointment> addToWaitingQueue(Customer customer, RawMaterial rawMaterial, String licensePlate, LocalDateTime simulatedTimeOfRegistration) {

        // Find First Free Slot
        LocalDateTime slot = findFirstFreeOutOfHoursSlot(simulatedTimeOfRegistration);

        Appointment appointment = new Appointment(customer, slot, rawMaterial, licensePlate, AppointmentType.WAITING_QUEUE);

        try {
            return Optional.of(this.appointmentRepository.save(appointment));
        } catch (DataIntegrityViolationException e) {
            return Optional.empty();
        }
    }

    private LocalDateTime findFirstFreeOutOfHoursSlot(LocalDateTime simulatedTimeOfRegistration) {
        LocalDateTime firstFreeSlot = null;
        LocalDateTime firstPossibleSlot = simulatedTimeOfRegistration.truncatedTo(ChronoUnit.HOURS);
        List<AppointmentCountPerHour> slots = appointmentRepository.CountAppointmentsPerSlot(simulatedTimeOfRegistration);

        while(firstFreeSlot == null) {

            LocalDateTime currentTargetTime = firstPossibleSlot; // To circumvent lambda's need for final vars

            boolean afterAppointments = currentTargetTime.getHour() >= config.getAppointmentsEndHour();
            boolean beforeAppointments = currentTargetTime.getHour() < config.getAppointmentsStartHour();

            boolean slotFree = slots.stream().filter(slot -> slot.getSlot().equals(currentTargetTime))
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

    public Optional<Appointment> findValidAppointment(String licensePlate, LocalDateTime arrivalTime) {

        List<Appointment> appointments = appointmentRepository.findByLicensePlate(licensePlate);

        return appointments.stream().filter(a -> isInSlot(a.getSlot(), arrivalTime)).findAny();
    }

    private boolean isInSlot(LocalDateTime slot, LocalDateTime arrivalTime) {
        return arrivalTime.isAfter(slot.minusSeconds(1)) && arrivalTime.isBefore(slot.plusMinutes(config.getSlotDuration()));
    }

}
