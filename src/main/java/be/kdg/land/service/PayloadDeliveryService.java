package be.kdg.land.service;

import be.kdg.land.domain.PayloadDelivery;
import be.kdg.land.domain.RawMaterial;
import be.kdg.land.domain.Warehouse;
import be.kdg.land.domain.customer.Customer;
import be.kdg.land.domain.passage.Entry;
import be.kdg.land.domain.weighment.Weighing;
import be.kdg.land.repository.PayloadDeliveryRepository;
import be.kdg.land.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PayloadDeliveryService {

    @Autowired
    private PayloadDeliveryRepository payloadDeliveryRepository;
    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private WeighingService weighingService;

    public PayloadDelivery addPayloadDeliveryOnEntry(Customer customer, RawMaterial rawMaterial, String licensePlate, Entry entry) {
        PayloadDelivery payloadDelivery = new PayloadDelivery(customer, rawMaterial, licensePlate, entry);

        // Request Weighbridge Assignment
        Optional<Weighing> newWeighingOptional = weighingService.getNewWeighbridgeAssignment(payloadDelivery.getLicensePlate());
        if (newWeighingOptional.isEmpty()) {
            throw new RuntimeException("Unable to save new Weighing");
        }

        payloadDelivery.setEntryWeighing(newWeighingOptional.get());

        //TODO Check Validation
        payloadDeliveryRepository.save(payloadDelivery);

        return payloadDelivery;
    }


    public Optional<PayloadDelivery> addNewExitWeighing(String licensePlate) {
        Optional<PayloadDelivery> payloadDeliveryOpt = payloadDeliveryRepository.findByLicensePlateAndExitIsNullAndEntryIsNotNull(licensePlate);
        if (payloadDeliveryOpt.isEmpty()) {
            throw new RuntimeException("Payload delivery for license Plate " + licensePlate + " not found");
        }

        PayloadDelivery payloadDelivery = payloadDeliveryOpt.get();

        Optional<Weighing> newWeighingOptional = weighingService.getNewWeighbridgeAssignment(payloadDelivery.getLicensePlate());
        if (newWeighingOptional.isEmpty()) {
            throw new RuntimeException("Unable to save new Weighing");
        }

        payloadDelivery.setExitWeighing(newWeighingOptional.get());

        return Optional.of(payloadDeliveryRepository.save(payloadDelivery));

    }


    public Optional<Warehouse> getWarehouseAssignment(String licensePlate) {
        Optional<PayloadDelivery> payloadDeliveryOpt = payloadDeliveryRepository.findByLicensePlateAndExitIsNullAndEntryIsNotNull(licensePlate);

        if (payloadDeliveryOpt.isEmpty()) {
            throw new RuntimeException("Payload delivery for license Plate " + licensePlate + " not found");
        }

        PayloadDelivery payloadDelivery = payloadDeliveryOpt.get();

        if (payloadDelivery.getWarehouse() == null) {
            Optional<Warehouse> warehouse = warehouseRepository.findByCustomer_CustomerIdAndRawMaterial_RawMaterialId(payloadDelivery.getCustomer().getCustomerId(), payloadDelivery.getRawMaterial().getRawMaterialId());
            if (warehouse.isEmpty()) {
                throw new RuntimeException("Warehouse not found");
            }

            payloadDelivery.setWarehouse(warehouse.get());
            payloadDeliveryRepository.save(payloadDelivery);
            return warehouse;
        }
        return Optional.of(payloadDelivery.getWarehouse());
    }

    public String getDirections(String licensePlate) {
        Optional<PayloadDelivery> payloadDeliveryOpt = payloadDeliveryRepository.findByLicensePlateAndExitIsNullAndEntryIsNotNull(licensePlate);

        if (payloadDeliveryOpt.isEmpty()) {
            return "Access the terrain by checking in at the Gate.";
        }

        String standardMessage = "Please go to ";

        PayloadDelivery payloadDelivery = payloadDeliveryOpt.get();
        if (payloadDelivery.getWarehouse() == null) {
            return standardMessage + payloadDelivery.getEntryWeighing().getWeighBridge();
        }

        else if (payloadDelivery.getExitWeighing() == null) {
            return standardMessage + payloadDelivery.getWarehouse().getWarehouseId().toString();
        }

        else if (payloadDelivery.getExit() == null && payloadDelivery.getExitWeighing().getTimestamp() == null) {
            return standardMessage + payloadDelivery.getExitWeighing().getWeighBridge();
        }

        return standardMessage + "EXIT";
    }
}
