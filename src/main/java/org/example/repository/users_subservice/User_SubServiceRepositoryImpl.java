package org.example.repository.users_subservice;

import org.example.base.repository.BaseRepositoryImpl;
import org.example.model.Expert;
import org.example.model.Subservice;
import org.example.model.User_SubService;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class User_SubServiceRepositoryImpl extends BaseRepositoryImpl<User_SubService, Long>
    implements User_SubServiceRepository {
    public User_SubServiceRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Class<User_SubService> getClassName() {
        return User_SubService.class;
    }

    @Override
    public Optional<List<User_SubService>> findByExpert(Expert expert) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM User_SubService s WHERE s.expert = :expert";
        Query<User_SubService> query = session.createQuery(hql, getClassName())
                .setParameter("expert", expert);
        List<User_SubService> resultList = query.getResultList();
        if (resultList.isEmpty())
            return Optional.empty();
        return Optional.of(resultList);
    }

    @Override
    public Optional<List<User_SubService>> findBySubservice(Subservice subservice) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM User_SubService s WHERE s.subservice = :subservice";
        Query<User_SubService> query = session.createQuery(hql, getClassName())
                .setParameter("subservice", subservice);
        List<User_SubService> resultList = query.getResultList();
        if (resultList.isEmpty())
            return Optional.empty();
        return Optional.of(resultList);
    }

    @Override
    public Optional<User_SubService> findByExpertAndSubservice(Expert expert, Subservice subservice) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM User_SubService s WHERE s.expert = :expert AND s.subservice = :subservice";
        Query<User_SubService> query = session.createQuery(hql, getClassName())
                .setParameter("expert", expert)
                .setParameter("subservice", subservice);
        List<User_SubService> resultList = query.getResultList();
        if (resultList.isEmpty())
            return Optional.empty();
        return Optional.of(resultList.get(0));
    }
}
