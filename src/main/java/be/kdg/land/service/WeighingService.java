package be.kdg.land.service;

import be.kdg.land.config.LandApplicationConfig;
import be.kdg.land.domain.PayloadDelivery;
import be.kdg.land.domain.weighment.Weighing;
import be.kdg.land.messaging.DeliverySender;
import be.kdg.land.repository.PayloadDeliveryRepository;
import be.kdg.land.repository.WeighingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class WeighingService {

    private final LandApplicationConfig config;
    private final DeliverySender deliverySender;
    private final WeighingRepository weighingRepository;
    private final PayloadDeliveryRepository payloadDeliveryRepository;


    public WeighingService(LandApplicationConfig config, DeliverySender deliverySender, WeighingRepository weighingRepository, PayloadDeliveryRepository payloadDeliveryRepository) {
        this.config = config;
        this.deliverySender = deliverySender;
        this.weighingRepository = weighingRepository;
        this.payloadDeliveryRepository = payloadDeliveryRepository;
    }

    public Optional<Weighing> getNewWeighbridgeAssignment(String licensePlate) {

        Weighing newWeighing = new Weighing(config.getWeighbridgeName(), licensePlate);

        return Optional.of(weighingRepository.save(newWeighing));
    }

    public Optional<Weighing> addWeighing(String LicensePlate, LocalDateTime timestamp, double weight) {

        Optional<PayloadDelivery> payloadDeliveryOptional = payloadDeliveryRepository.findByLicensePlateAndExitIsNullAndEntryIsNotNull(LicensePlate);
        if (payloadDeliveryOptional.isEmpty()) {
            return Optional.empty();
        }

        PayloadDelivery payloadDelivery = payloadDeliveryOptional.get();


        boolean exitWeighing = payloadDelivery.getExitWeighing() != null;
        Weighing weighing;
        if (exitWeighing) {
            weighing = payloadDelivery.getExitWeighing();
            weighing.setTimestamp(timestamp);
            weighing.setWeight(weight);

            deliverySender.sendDelivery(payloadDelivery);

        } else {
            weighing = payloadDelivery.getEntryWeighing();
            weighing.setTimestamp(timestamp);
            weighing.setWeight(weight);
        }

        return Optional.of(weighingRepository.save(weighing));
    }

}
