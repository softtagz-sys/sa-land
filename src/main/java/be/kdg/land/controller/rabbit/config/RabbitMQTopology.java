package be.kdg.land.controller.rabbit.config;

import be.kdg.land.LandApplicationConfig;
import be.kdg.land.RabbitConfig;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQTopology {

    @Autowired
    RabbitConfig rabbitConfig;

    @Bean
    TopicExchange landExchange() {
        return new TopicExchange(rabbitConfig.getLandExchange());
    }

    @Bean
    public Queue topicQueueWeighbridge() {
        return new Queue(rabbitConfig.getQueueWeighbridge());
    }

    @Bean
    public Binding topicWeighbridgeBinding(TopicExchange landExchange, Queue topicQueueWeighbridge) {
        return BindingBuilder.bind(topicQueueWeighbridge).to(landExchange).with(rabbitConfig.getKeyWeighbridge());
    }
}
