package be.kdg.land.domain.weighment;


import java.util.UUID;

public class WeighBridgeTicket {

    private final UUID weighBridgeTicketId;
    private Weighing initialWeighing;
    private Weighing finalWeighing;

    public WeighBridgeTicket(UUID weighBridgeTicketId) {
        this.weighBridgeTicketId = weighBridgeTicketId;
    }
}
