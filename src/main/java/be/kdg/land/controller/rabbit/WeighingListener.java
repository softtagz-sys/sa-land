package be.kdg.land.controller.rabbit;

import be.kdg.land.LandApplicationConfig;
import be.kdg.land.RabbitConfig;
import be.kdg.land.controller.dto.in.WeighingOperationDto;
import be.kdg.land.controller.rabbit.config.RabbitMQTopology;
import be.kdg.land.domain.weighment.Weighing;
import be.kdg.land.service.PayloadDeliveryService;
import be.kdg.land.service.WeighingService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class WeighingListener {

    @Autowired
    RabbitConfig rabbitConfig;

    @Autowired
    WeighingService weighingService;
    @Autowired
    PayloadDeliveryService payloadDeliveryService;

// TODO add validation input

    @RabbitListener(queues = "#{@rabbitConfig.getQueueWeighbridge()}")
    public void addWeighing(WeighingOperationDto weighingOperationDto) {
        weighingService.addWeighing(weighingOperationDto.getLicensePlate(), weighingOperationDto.getTimestamp(), weighingOperationDto.getWeight());
        payloadDeliveryService.getWarehouseAssignment(weighingOperationDto.getLicensePlate());

    }

}
