package org.example.base.service;


import org.example.base.exception.Exception;
import org.example.base.model.BaseEntity;
import org.example.base.repository.BaseRepository;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.io.Serializable;

public class BaseServiceImpl<T extends BaseEntity<ID>,
        ID extends Serializable,
        R extends BaseRepository<T, ID>>
        implements BaseService<T, ID> {

    protected final R repository;
    protected final SessionFactory sessionFactory;

    public BaseServiceImpl(R repository, SessionFactory sessionFactory) {
        this.repository = repository;
        this.sessionFactory = sessionFactory;
    }

    @Override
    public T saveOrUpdate(T entity) {
        Transaction transaction = null;
        try(Session session = sessionFactory.getCurrentSession()){
            transaction = session.beginTransaction();
            T t = repository.saveOrUpdate(entity);
            transaction.commit();
            return t;
        } catch (Exception e){
            assert transaction != null;
            transaction.rollback();
            return null;
        }
    }

    @Override
    public T findById(ID id) {
        try (Session session = sessionFactory.getCurrentSession()){
            session.beginTransaction();
            T foundEntity = repository.findById(id).orElseThrow(
                    () -> new Exception(String.format("Couldn't find an Entity with %s !! ", id))
            );
            session.getTransaction().commit();
            return foundEntity;
        } catch (HibernateException e){
            return null;
        }
    }

    @Override
    public void delete(T entity) {
        Transaction transaction = null;
        try (Session session = sessionFactory.getCurrentSession()) {
            transaction = session.beginTransaction();
            repository.delete(entity);
            transaction.commit();
        } catch (java.lang.Exception e) {
            assert transaction != null;
            transaction.rollback();
        }
    }
}
