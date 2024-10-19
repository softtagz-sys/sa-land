package be.kdg.land.config.rabbit;

import be.kdg.land.config.RabbitConfig;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQTopology {


    private final RabbitConfig rabbitConfig;

    public RabbitMQTopology(RabbitConfig rabbitConfig) {
        this.rabbitConfig = rabbitConfig;
    }

    @Bean
    TopicExchange mineralFlowExchange() {
        return new TopicExchange(rabbitConfig.getMineralFlowExchange());
    }

    @Bean
    public Queue topicQueueDelivery() { return new Queue(rabbitConfig.getDeliveryQueue());}

    @Bean
    public Queue topicQueueWeighbridge() {
        return new Queue(rabbitConfig.getQueueWeighbridge());
    }

    @Bean
    public Binding topicWeighbridgeBinding(TopicExchange mineralFlowExchange, Queue topicQueueWeighbridge) {
        return BindingBuilder.bind(topicQueueWeighbridge).to(mineralFlowExchange).with(rabbitConfig.getKeyWeighbridge());
    }

    @Bean
    public Binding topicDeliveryBinding(TopicExchange mineralFlowExchange, Queue topicQueueDelivery) {
        return BindingBuilder.bind(topicQueueDelivery).to(mineralFlowExchange).with(rabbitConfig.getWarehouseDeliveryKey());
    }
}
