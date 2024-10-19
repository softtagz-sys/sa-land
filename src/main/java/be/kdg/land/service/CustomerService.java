package be.kdg.land.service;

import be.kdg.land.domain.customer.Customer;
import be.kdg.land.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {


    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    public Optional<Customer> getCustomerByName(String name) {
        return customerRepository.findByNameIgnoreCase(name);
    }
}
