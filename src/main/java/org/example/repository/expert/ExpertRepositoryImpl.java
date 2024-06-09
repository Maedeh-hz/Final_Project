package org.example.repository.expert;

import org.example.base.repository.BaseRepositoryImpl;
import org.example.exception.NoResultException;
import org.example.model.Customer;
import org.example.model.Expert;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class ExpertRepositoryImpl extends BaseRepositoryImpl<Expert, Long>
        implements ExpertRepository {
    public ExpertRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Class<Expert> getClassName() {
        return Expert.class;
    }

    @Override
    public Optional<Expert> findByUsername(String username) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM Expert e WHERE e.username = :username";
        Query<Expert> query = session.createQuery(hql, getClassName())
                .setParameter("username", username);
        List<Expert> resultList = query.getResultList();
        if (resultList.isEmpty())
            return Optional.empty();
        return Optional.of(resultList.get(0));
    }

    @Override
    public Optional<List<Expert>> loadAllWaitingForVerificationExperts() {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM Expert e where e.expertsLevel = 'WAITING_FOR_VERIFYING' ";
        Query<Expert> query = session.createQuery(hql, getClassName());
        List<Expert> experts = query.getResultList();
        if (experts.isEmpty())
            return Optional.empty();
        return Optional.of(experts);
    }

    @Override
    public Optional<List<Expert>> loadAllVerifiedExperts() {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM Expert e where e.expertsLevel = 'VERIFIED' ";
        Query<Expert> query = session.createQuery(hql, getClassName());
        List<Expert> experts = query.getResultList();
        if (experts.isEmpty())
            return Optional.empty();
        return Optional.of(experts);
    }
}
