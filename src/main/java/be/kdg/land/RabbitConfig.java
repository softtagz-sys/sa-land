package be.kdg.land;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "rabbit")
public class RabbitConfig {

    private String WarehouseExchange;
    private String LandExchange;
    private String QueueWeighbridge;
    private String KeyWeighbridge;
}
