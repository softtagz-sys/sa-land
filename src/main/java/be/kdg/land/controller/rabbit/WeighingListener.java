package be.kdg.land.controller.rabbit;

import be.kdg.land.config.RabbitConfig;
import be.kdg.land.controller.dto.in.WeighingOperationDto;
import be.kdg.land.service.PayloadDeliveryService;
import be.kdg.land.service.WeighingService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
