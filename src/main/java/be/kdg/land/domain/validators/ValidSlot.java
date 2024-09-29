package be.kdg.land.domain.validators;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ValidSlotValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidSlot {
    String message() default "A Slot can only begin on full hours between the business hours";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
