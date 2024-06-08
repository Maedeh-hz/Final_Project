package org.example.repository.user;

import org.example.base.repository.BaseRepositoryImpl;
import org.example.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class UserRepositoryImpl extends BaseRepositoryImpl<User, Long>
        implements UserRepository {
    public UserRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Class<User> getClassName() {
        return User.class;
    }

    @Override
    public Optional<String> findTypeByUsername(String username) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM User u WHERE u.username = :username ";
        Query<User> query = session.createQuery(hql, getClassName())
                .setParameter("username" , username);
        List<User> resultList = query.getResultList();
        if (resultList.isEmpty())
            return Optional.empty();
        return Optional.of(resultList.get(0).getDtype());
    }
}
