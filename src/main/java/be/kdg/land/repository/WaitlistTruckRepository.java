package be.kdg.land.repository;

import be.kdg.land.domain.waitlist.TruckOnWaitlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface WaitlistTruckRepository extends JpaRepository<TruckOnWaitlist, UUID> {

//    Optional<TruckOnWaitlist> findByLicensePlate(String licensePlate);
}
