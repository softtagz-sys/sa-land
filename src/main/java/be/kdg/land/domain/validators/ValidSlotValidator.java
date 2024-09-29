package be.kdg.land.domain.validators;

import be.kdg.land.LandApplicationConfig;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

public class ValidSlotValidator implements ConstraintValidator<ValidSlot, LocalDateTime> {


//    @Value("${app.appointmentsStartHour}")
    private int appointmentsStartHour = 8;
//
//    @Value("${app.appointmentsEndHour}")
    private int appointmentsEndHour = 20;




    @Override
    public boolean isValid(LocalDateTime slotTime, ConstraintValidatorContext constraintValidatorContext) {
        if (slotTime == null) {
            return true;  // delegated to @NotNull annotation
        }

        boolean withinTimeRange = slotTime.getHour() >= appointmentsStartHour && slotTime.getHour() <= appointmentsEndHour;
        boolean isOnFullHour = slotTime.getMinute() == 0;

        return withinTimeRange && isOnFullHour;

    }

    @Override
    public void initialize(ValidSlot constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
}
