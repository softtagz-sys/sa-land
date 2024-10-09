package be.kdg.land.service;

import be.kdg.land.domain.PayloadDelivery;
import be.kdg.land.domain.RawMaterial;
import be.kdg.land.domain.customer.Customer;
import be.kdg.land.domain.passage.Entry;
import be.kdg.land.domain.weighment.Weighing;
import be.kdg.land.repository.PayloadDeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PayloadDeliveryService {

    @Autowired
    private PayloadDeliveryRepository payloadDeliveryRepository;

    @Autowired
    private WeighingService weighingService;

    public PayloadDelivery addPayloadDeliveryOnEntry(Customer customer, RawMaterial rawMaterial, String licensePlate, Entry entry) {
        PayloadDelivery payloadDelivery = new PayloadDelivery(customer, rawMaterial, licensePlate, entry);

        // Request Weighbridge Assignment
        Weighing newWeighing = weighingService.getNewWeighbridgeAssignment(licensePlate);
        payloadDelivery.setEntryWeighing(newWeighing);

        //TODO Check Validation
        payloadDeliveryRepository.save(payloadDelivery);

        return payloadDelivery;
    }
}
