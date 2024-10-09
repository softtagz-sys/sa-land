package be.kdg.land.service.exceptions;

public class NoValidAppointmentException extends RuntimeException {
    public NoValidAppointmentException(String message) {
        super(message);
    }
}
