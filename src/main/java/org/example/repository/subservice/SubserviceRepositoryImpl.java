package org.example.repository.subservice;

import org.example.base.repository.BaseRepositoryImpl;
import org.example.exception.NoResultException;
import org.example.model.Service;
import org.example.model.Subservice;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class SubserviceRepositoryImpl extends BaseRepositoryImpl<Subservice, Long>
        implements SubserviceRepository {
    public SubserviceRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Class<Subservice> getClassName() {
        return Subservice.class;
    }

    @Override
    public Optional<List<Subservice>> loadAll() {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM Subservice s ";
        Query<Subservice> query = session.createQuery(hql, getClassName());
        List<Subservice> list = query.list();
        if (list.isEmpty())
            return Optional.empty();
        return Optional.of(list);
    }

    @Override
    public Optional<List<Subservice>> loadAllByService(Service service) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM Subservice s where s.service = :service";
        Query<Subservice> query = session.createQuery(hql, getClassName())
                .setParameter("service", service);
        List<Subservice> list = query.list();
        if (list.isEmpty())
            return Optional.empty();
        return Optional.of(list);
    }

}
