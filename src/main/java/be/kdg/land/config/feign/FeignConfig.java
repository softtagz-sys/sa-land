package be.kdg.land.config.feign;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "be.kdg.land.messaging")
public class FeignConfig {
}
