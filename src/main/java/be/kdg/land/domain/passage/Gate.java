package be.kdg.land.domain.passage;

import jakarta.validation.constraints.NotBlank;

public class Gate {

    @NotBlank
    private final String location;

    public Gate(String location) {
        this.location = location;
    }

    public void open() {
        System.out.println("Gate " + location + " opened"); //TODO replace with logger
    }

    public void close() {
        System.out.println("Gate " + location + " closed"); //TODO replace with logger
    }
}
