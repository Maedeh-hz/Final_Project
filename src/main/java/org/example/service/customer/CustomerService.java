package org.example.service.customer;

import org.example.base.service.BaseService;
import org.example.model.Customer;

public interface CustomerService extends BaseService<Customer, Long> {
    Customer findByUsername(String username);
}
