package be.kdg.land.domain;

import be.kdg.land.domain.customer.Supplier;
import be.kdg.land.domain.passage.Entry;
import be.kdg.land.domain.passage.Exit;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class PayloadDelivery
{
    private final UUID payloadDeliveryId;

    @NotNull
    private final Supplier supplier;

    @NotNull
    private final RawMaterialType rawMaterialType;

    @NotNull
    private final Truck truck;

    @NotNull
    private final Entry checkInPassage;

    private Warehouse warehouse;
    private Exit checkOutPassage;

    public PayloadDelivery(UUID payloadDeliveryId, Supplier supplier, RawMaterialType rawMaterialType, Truck truck, Entry checkInPassage) {
        this.payloadDeliveryId = payloadDeliveryId;
        this.supplier = supplier;
        this.rawMaterialType = rawMaterialType;
        this.truck = truck;
        this.checkInPassage = checkInPassage;
    }

    public void setWarehouse(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public void setCheckOutPassage(Exit checkOutPassage) {
        this.checkOutPassage = checkOutPassage;
    }
}
