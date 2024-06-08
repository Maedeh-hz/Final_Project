package org.example.repository.customer;

import org.example.base.repository.BaseRepositoryImpl;
import org.example.model.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class CustomerRepositoryImpl extends BaseRepositoryImpl<Customer, Long>
        implements CustomerRepository {
    public CustomerRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Class<Customer> getClassName() {
        return Customer.class;
    }

    @Override
    public Optional<Customer> findByUsername(String username) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM Customer c WHERE c.username = :username";
        Query<Customer> query = session.createQuery(hql, getClassName())
                .setParameter("username", username);
        List<Customer> resultList = query.getResultList();
        if (resultList.isEmpty())
            return Optional.empty();
        return Optional.of(resultList.get(0));
    }
}
