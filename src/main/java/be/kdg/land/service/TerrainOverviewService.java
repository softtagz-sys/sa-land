package be.kdg.land.service;

import be.kdg.land.domain.PayloadDelivery;
import be.kdg.land.domain.appointment.Appointment;
import be.kdg.land.domain.appointment.AppointmentType;
import be.kdg.land.repository.AppointmentRepository;
import be.kdg.land.repository.PayloadDeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class TerrainOverviewService {

    @Autowired AppointmentRepository appointmentRepository;
    @Autowired PayloadDeliveryRepository payloadDeliveryRepository;

    public List<Appointment> getArrivals(LocalDateTime slot) {
        LocalDateTime slotStart = slot.truncatedTo(ChronoUnit.HOURS);
        LocalDateTime slotEnd = slotStart.plusHours(1);
        return appointmentRepository.findApppointmentsInSlotWithType(slotStart, slotEnd, AppointmentType.APPOINTMENT);
    }

    public List<Appointment> getQueue(LocalDateTime simulatedTime) {
        return appointmentRepository.findAppointmentsAfterSlotWithType(simulatedTime, AppointmentType.WAITING_QUEUE)
                .stream().filter(a -> a.getEntry() == null).toList();
    }

    public List<PayloadDelivery> getPayloadDeliveriesInProgress() {
        return payloadDeliveryRepository.getPayloadDeliveriesWithCustomerByEntryIsNotNullAndExitIsNull();
    }
}
