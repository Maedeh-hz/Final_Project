package org.example.base.repository;

import org.example.base.model.BaseEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.Serializable;
import java.util.Optional;

public abstract class BaseRepositoryImpl<T extends BaseEntity<ID>, ID extends Serializable>
        implements BaseRepository<T, ID> {

    protected final SessionFactory sessionFactory;

    public BaseRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public T saveOrUpdate(T entity) {
        Session session = sessionFactory.getCurrentSession();
        if (entity.getId() == null)
            session.persist(entity);
        else
            entity = session.merge(entity);
        return entity;
    }

    @Override
    public Optional<T> findById(ID id) {
        Session session = sessionFactory.getCurrentSession();
        return Optional.ofNullable(session.get(getClassName(), id));
    }

    @Override
    public void delete(T entity) {
        Session session = sessionFactory.getCurrentSession();
        session.remove(entity);
    }

    public abstract Class<T> getClassName();
}
