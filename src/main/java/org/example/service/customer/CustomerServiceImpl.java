package org.example.service.customer;

import org.example.base.exception.Exception;
import org.example.base.service.BaseServiceImpl;
import org.example.model.Customer;
import org.example.repository.customer.CustomerRepository;
import org.example.repository.customer.CustomerRepositoryImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Optional;

public class CustomerServiceImpl extends BaseServiceImpl<Customer, Long, CustomerRepository>
        implements CustomerService {

    public CustomerServiceImpl(CustomerRepository repository, SessionFactory sessionFactory) {
        super(repository, sessionFactory);
    }

    @Override
    public Customer findByUsername(String username) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Optional<Customer> optional = repository.findByUsername(username);
            optional.orElseThrow(
                    () -> new Exception(String.format("User with username: %s not found",
                            username))
            );
            return optional.get();
        } catch (Exception e) {
            return null;
        }
    }
}
