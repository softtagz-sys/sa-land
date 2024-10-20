package be.kdg.land.controller.rabbit;

import be.kdg.land.config.RabbitConfig;
import be.kdg.land.controller.dto.in.WeighingOperationDto;
import be.kdg.land.service.PayloadDeliveryService;
import be.kdg.land.service.WeighingService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Controller
public class WeighingListener {

    private final RabbitConfig rabbitConfig; // Gebruikt in RabbitListener Annotation

    private final WeighingService weighingService;
    private final PayloadDeliveryService payloadDeliveryService;




    public WeighingListener(RabbitConfig rabbitConfig, WeighingService weighingService, PayloadDeliveryService payloadDeliveryService) {
        this.rabbitConfig = rabbitConfig;
        this.weighingService = weighingService;
        this.payloadDeliveryService = payloadDeliveryService;
    }


    // TODO add validation input
    @RabbitListener(queues = "#{@rabbitConfig.getQueueWeighbridge()}")
    public void addWeighing(WeighingOperationDto weighingOperationDto) {
        weighingService.addWeighing(weighingOperationDto.getLicensePlate(), weighingOperationDto.getTimestamp(), weighingOperationDto.getWeight());
        payloadDeliveryService.getWarehouseAssignment(weighingOperationDto.getLicensePlate());

    }

}
