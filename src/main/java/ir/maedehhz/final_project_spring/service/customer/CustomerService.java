package ir.maedehhz.final_project_spring.service.customer;

import ir.maedehhz.final_project_spring.model.Customer;

public interface CustomerService {
    Customer save(Customer customer);

    Customer findById(long customerId);

    Customer findByEmail(String email);

    void enableCustomer(String username);

    Customer updatePassword(long customerId, String newPass, String newPass2);
}
