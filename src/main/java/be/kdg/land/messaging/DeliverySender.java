package be.kdg.land.messaging;

import be.kdg.land.config.RabbitConfig;
import be.kdg.land.config.rabbit.RabbitMQTopology;
import be.kdg.land.domain.PayloadDelivery;
import be.kdg.land.messaging.dto.DeliveryCompleteDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeliverySender {

    @Autowired RabbitTemplate rabbitTemplate;
    @Autowired RabbitConfig rabbitConfig;

    public void sendDelivery(PayloadDelivery payloadDelivery) {

        DeliveryCompleteDto delivery = new DeliveryCompleteDto(
                payloadDelivery.getCustomer().getCustomerId().toString(),
                payloadDelivery.getRawMaterial().getName(),
                payloadDelivery.getEntryWeighing().getWeight() - payloadDelivery.getExitWeighing().getWeight(),
                payloadDelivery.getExitWeighing().getTimestamp()
        );


        rabbitTemplate.convertAndSend(
                rabbitConfig.getMineralFlowExchange(),
                rabbitConfig.getWarehouseDeliveryKey(),
                delivery
                );
    }
}
