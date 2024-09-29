//package be.kdg.land.domain;
//
//import be.kdg.land.domain.customer.Customer;
//import be.kdg.land.domain.passage.Entry;
//import be.kdg.land.domain.passage.Exit;
//import jakarta.validation.constraints.NotNull;
//
//import java.util.UUID;
//
//public class PayloadDelivery
//{
//    private UUID payloadDeliveryId;
//
//    @NotNull
//    private final Customer customer;
//
//    @NotNull
//    private final RawMaterialType rawMaterialType;
//
//    @NotNull
//    private final Truck truck;
//
//    @NotNull
//    private final Entry checkInPassage;
//
//    private Warehouse warehouse;
//    private Exit checkOutPassage;
//
//    public PayloadDelivery(Customer customer, RawMaterialType rawMaterialType, Truck truck, Entry checkInPassage) {
//        this.customer = customer;
//        this.rawMaterialType = rawMaterialType;
//        this.truck = truck;
//        this.checkInPassage = checkInPassage;
//    }
//
//    public void setWarehouse(Warehouse warehouse) {
//        this.warehouse = warehouse;
//    }
//
//    public void setCheckOutPassage(Exit checkOutPassage) {
//        this.checkOutPassage = checkOutPassage;
//    }
//}
