package org.example.repository.admin;

import org.example.base.repository.BaseRepository;
import org.example.base.repository.BaseRepositoryImpl;
import org.example.model.Admin;
import org.example.model.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class AdminRepositoryImpl extends BaseRepositoryImpl<Admin, Long>
        implements AdminRepository {
    public AdminRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Class<Admin> getClassName() {
        return Admin.class;
    }

    @Override
    public Optional<Admin> findByUsername(String username) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM Admin a WHERE a.username = :username";
        Query<Admin> query = session.createQuery(hql, getClassName())
                .setParameter("username", username);
        List<Admin> resultList = query.getResultList();
        if (resultList.isEmpty())
            return Optional.empty();
        return Optional.of(resultList.get(0));
    }
}
