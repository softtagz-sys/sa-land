package be.kdg.land.repository;

import be.kdg.land.domain.PayloadDelivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface PayloadDeliveryRepository extends JpaRepository<PayloadDelivery, UUID> {
}
