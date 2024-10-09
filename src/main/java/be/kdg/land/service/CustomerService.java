package be.kdg.land.service;

import be.kdg.land.domain.customer.Customer;
import be.kdg.land.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;


    public Optional<Customer> getCustomerById(UUID customerId) {
        return customerRepository.findById(customerId);
    }

    public Optional<Customer> getCustomerByName(String name) {
        return customerRepository.findByNameIgnoreCase(name);
    }
}
