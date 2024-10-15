package be.kdg.land.repository;

import be.kdg.land.domain.RawMaterial;
import be.kdg.land.domain.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, UUID> {

    Optional<Warehouse> findByCustomer_CustomerIdAndRawMaterial_RawMaterialId(UUID customerId, UUID rawMaterial);
}
