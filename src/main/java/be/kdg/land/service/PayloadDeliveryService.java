package be.kdg.land.service;

import be.kdg.land.domain.PayloadDelivery;
import be.kdg.land.domain.RawMaterial;
import be.kdg.land.domain.customer.Customer;
import be.kdg.land.domain.passage.Entry;
import be.kdg.land.domain.weighment.Weighing;
import be.kdg.land.repository.PayloadDeliveryRepository;
import org.springframework.stereotype.Service;

@Service
public class PayloadDeliveryService {


    private PayloadDeliveryRepository payloadDeliveryRepository;
    private WeighingService weighingService;


    public PayloadDeliveryService(PayloadDeliveryRepository payloadDeliveryRepository, WeighingService weighingService) {
        this.payloadDeliveryRepository = payloadDeliveryRepository;
        this.weighingService = weighingService;
    }

    public PayloadDelivery addPayloadDeliveryOnEntry(Customer customer, RawMaterial rawMaterial, String licensePlate, Entry entry) {
        PayloadDelivery payloadDelivery = new PayloadDelivery(customer, rawMaterial, licensePlate, entry);

        // Request Weighbridge Assignment
        Weighing newWeighing = weighingService.GetNewWeighbridgeAssignment(licensePlate);
        payloadDelivery.setEntryWeighing(newWeighing);

        payloadDeliveryRepository.save(payloadDelivery);

        return payloadDelivery;
    }


}
