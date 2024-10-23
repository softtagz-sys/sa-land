package be.kdg.land.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "rabbit")
public class RabbitConfig {

    private String mineralFlowExchange;
    private String deliveryQueue;
    private String warehouseDeliveryKey;
    private String queueWeighbridge;
    private String keyWeighbridge;
}
