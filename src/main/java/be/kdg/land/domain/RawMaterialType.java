package be.kdg.land.domain;

import jakarta.validation.constraints.NotEmpty;

public class RawMaterialType {

    @NotEmpty
    private final String name;

    public RawMaterialType(String name) {
        this.name = name;
    }
}
