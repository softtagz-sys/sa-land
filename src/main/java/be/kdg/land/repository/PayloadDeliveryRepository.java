package be.kdg.land.repository;

import be.kdg.land.domain.PayloadDelivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PayloadDeliveryRepository extends JpaRepository<PayloadDelivery, UUID> {

    Optional<PayloadDelivery> findByLicensePlateAndExitIsNullAndEntryIsNotNull(String licensePlate);

    List<PayloadDelivery> getPayloadDeliveriesByLicensePlateContainingIgnoreCase(String licensePlate);
}
