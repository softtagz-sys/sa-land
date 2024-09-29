package be.kdg.land.repository;

import be.kdg.land.domain.RawMaterial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RawMaterialRepository extends JpaRepository<RawMaterial, UUID> {
    Optional<RawMaterial> findByRawMaterialId(UUID rawMaterialId);
    Optional<RawMaterial> findByNameIgnoreCase(String name);
}
