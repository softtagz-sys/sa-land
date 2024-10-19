package be.kdg.land;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
public class LandApplication {

    public static void main(String[] args) {
        SpringApplication.run(LandApplication.class, args);
    }

}
