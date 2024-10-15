package be.kdg.land.repository;

import be.kdg.land.domain.weighment.Weighing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface WeighingRepository extends JpaRepository<Weighing, UUID> {

}
