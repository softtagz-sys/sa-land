package be.kdg.land.service;

import be.kdg.land.domain.PayloadDelivery;
import be.kdg.land.repository.PayloadDeliveryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TicketService {


    private final PayloadDeliveryRepository payloadDeliveryRepository;

    public TicketService(PayloadDeliveryRepository payloadDeliveryRepository) {
        this.payloadDeliveryRepository = payloadDeliveryRepository;
    }

    public List<PayloadDelivery> getAllPayLoadDeliveries(String licensePlate) {
        return payloadDeliveryRepository.getPayloadDeliveriesByLicensePlateContainingIgnoreCase(licensePlate);
    }

    public Optional<PayloadDelivery> getPayloadDelivery(UUID payloadDeliveryId) {
        return payloadDeliveryRepository.findById(payloadDeliveryId);
    }
}
