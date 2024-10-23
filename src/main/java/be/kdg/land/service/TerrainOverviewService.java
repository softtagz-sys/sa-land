package be.kdg.land.service;

import be.kdg.land.domain.PayloadDelivery;
import be.kdg.land.domain.appointment.Appointment;
import be.kdg.land.domain.appointment.AppointmentType;
import be.kdg.land.repository.AppointmentRepository;
import be.kdg.land.repository.PayloadDeliveryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class TerrainOverviewService {

    private final AppointmentRepository appointmentRepository;
    private final PayloadDeliveryRepository payloadDeliveryRepository;

    public TerrainOverviewService(AppointmentRepository appointmentRepository, PayloadDeliveryRepository payloadDeliveryRepository) {
        this.appointmentRepository = appointmentRepository;
        this.payloadDeliveryRepository = payloadDeliveryRepository;
    }

    public List<Appointment> getArrivals(LocalDateTime slot) {
        LocalDateTime slotStart = slot.truncatedTo(ChronoUnit.HOURS);
        LocalDateTime slotEnd = slotStart.plusHours(1);
        return appointmentRepository.findAppointmentsByAppointmentTypeAndSlotGreaterThanEqualAndSlotBefore(AppointmentType.APPOINTMENT, slotStart, slotEnd);
    }

    public List<Appointment> getQueue(LocalDateTime simulatedTime) {
        LocalDateTime slotStart = simulatedTime.truncatedTo(ChronoUnit.HOURS);
          return appointmentRepository.findAppointmentsByAppointmentTypeAndSlotGreaterThanEqual(AppointmentType.WAITING_QUEUE, slotStart)
                .stream().filter(a -> a.getEntry() == null).toList();
    }

    public List<PayloadDelivery> getPayloadDeliveriesInProgress() {
        return payloadDeliveryRepository.getPayloadDeliveriesWithCustomerByEntryIsNotNullAndExitIsNull();
    }
}
