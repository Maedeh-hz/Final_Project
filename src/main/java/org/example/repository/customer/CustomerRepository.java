package org.example.repository.customer;

import org.example.base.repository.BaseRepository;
import org.example.model.Customer;

import java.util.Optional;

public interface CustomerRepository extends BaseRepository<Customer, Long> {
    Optional<Customer> findByUsername(String username);
}
