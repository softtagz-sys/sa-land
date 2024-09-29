package be.kdg.land;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "app")
public class LandApplicationConfig {

    private int maxAmountOfAppointmentsPerSlot;
    private int slotDuration;
    private int appointmentsStartHour;
    private int appointmentsEndHour;


}
