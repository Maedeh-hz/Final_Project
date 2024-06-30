package ir.maedehhz.final_project_spring.service.customer;

import ir.maedehhz.final_project_spring.model.Customer;

public interface CustomerService {
    Customer save(Customer customer);

    Customer findByUsername(String username);

    Customer findById(long customerId);

    Customer updatePassword(long customerId, String previousPass, String newPass, String newPass2);
}
