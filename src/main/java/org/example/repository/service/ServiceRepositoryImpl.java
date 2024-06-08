package org.example.repository.service;

import org.example.base.repository.BaseRepositoryImpl;
import org.example.exception.NoResultException;
import org.example.model.Service;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class ServiceRepositoryImpl extends BaseRepositoryImpl<Service, Long>
        implements ServiceRepository {
    public ServiceRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Class<Service> getClassName() {
        return Service.class;
    }

    @Override
    public Optional<List<Service>> loadAll() {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM Service s ";
        Query<Service> query = session.createQuery(hql, getClassName());
        List<Service> serviceList = query.getResultList();
        return Optional.ofNullable(Optional.of(serviceList).orElseThrow(() -> new NoResultException("No service found.")));
    }
}
