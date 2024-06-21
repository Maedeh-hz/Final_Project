package ir.maedehhz.final_project_spring.service.customer;

import ir.maedehhz.final_project_spring.model.Customer;

public interface CustomerService {
    Customer save(Customer customer);

    Customer findByUsername(String username);

    Customer updatePassword(Customer customer, String newPass, String newPass2);
}
