package be.kdg.land.repository;

import be.kdg.land.domain.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {

        Optional<Customer> findByCustomerId(UUID customerId);

        Optional<Customer> findByNameIgnoreCase(String name);
}
