package be.kdg.land.service.exceptions;

public class MaxAmountAppointmentsSlotReached extends RuntimeException {
    public MaxAmountAppointmentsSlotReached(String message) {
        super(message);
    }
}
