package org.example.service.service;

import org.example.base.exception.Exception;
import org.example.base.service.BaseServiceImpl;
import org.example.model.Customer;
import org.example.model.Service;
import org.example.repository.service.ServiceRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class ServiceServiceImpl extends BaseServiceImpl<Service, Long, ServiceRepository>
        implements ServiceService {
    public ServiceServiceImpl(ServiceRepository repository, SessionFactory sessionFactory) {
        super(repository, sessionFactory);
    }

    @Override
    public List<Service> loadAll() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Optional<List<Service>> optional = repository.loadAll();
            optional.orElseThrow(
                    () -> new Exception("No Service founded."));
            return optional.get();
        } catch (Exception e) {
            return null;
        }
    }
}
