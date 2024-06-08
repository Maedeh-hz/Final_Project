package org.example.repository.subservice;

import org.example.base.repository.BaseRepositoryImpl;
import org.example.exception.NoResultException;
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
        return Optional.ofNullable(Optional.of(query.list())
                .orElseThrow(() -> new NoResultException("No Subservice found.")));
    }
}
