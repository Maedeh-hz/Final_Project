package org.example.service.users_subservice;

import org.example.base.exception.Exception;
import org.example.base.service.BaseServiceImpl;
import org.example.exception.NoResultException;
import org.example.model.Expert;
import org.example.model.Subservice;
import org.example.model.User_SubService;
import org.example.repository.users_subservice.User_SubServiceRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class User_SubServiceServiceImpl extends BaseServiceImpl<User_SubService, Long, User_SubServiceRepository>
    implements User_SubServiceService {
    public User_SubServiceServiceImpl(User_SubServiceRepository repository, SessionFactory sessionFactory) {
        super(repository, sessionFactory);
    }

    @Override
    public List<User_SubService> findByExpert(Expert expert) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Optional<List<User_SubService>> optional = repository.findByExpert(expert);
            optional.orElseThrow(
                    () -> new NoResultException("Nothing found!")
            );
            return optional.get();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<User_SubService> findBySubservice(Subservice subservice) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Optional<List<User_SubService>> optional = repository.findBySubservice(subservice);
            optional.orElseThrow(
                    () -> new NoResultException("Nothing found!")
            );
            return optional.get();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public User_SubService findByExpertAndSubservice(Expert expert, Subservice subservice) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Optional<User_SubService> optional = repository.findByExpertAndSubservice(expert, subservice);
            optional.orElseThrow(
                    () -> new NoResultException("Nothing found!")
            );
            return optional.get();
        } catch (Exception e) {
            return null;
        }
    }
}
