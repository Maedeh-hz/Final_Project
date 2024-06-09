package org.example.service.expert;

import org.example.base.exception.Exception;
import org.example.base.service.BaseServiceImpl;
import org.example.exception.NoResultException;
import org.example.model.Customer;
import org.example.model.Expert;
import org.example.repository.expert.ExpertRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class ExpertServiceImpl extends BaseServiceImpl<Expert, Long, ExpertRepository>
        implements ExpertService {

    public ExpertServiceImpl(ExpertRepository repository, SessionFactory sessionFactory) {
        super(repository, sessionFactory);
    }

    @Override
    public Expert findByUsername(String username) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Optional<Expert> optional = repository.findByUsername(username);
            optional.orElseThrow(
                    () -> new Exception(String.format("User with username: %s not found",
                            username))
            );
            return optional.get();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Expert> loadAllWaitingForVerificationExperts() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Optional<List<Expert>> optional = repository.loadAllWaitingForVerificationExperts();
            optional.orElseThrow(
                    () -> new NoResultException("No waiting for verifying Expert found.")
            );
            return optional.get();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Expert> loadAllVerifiedExperts() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Optional<List<Expert>> optional = repository.loadAllVerifiedExperts();
            optional.orElseThrow(
                    () -> new NoResultException("No verified Expert found.")
            );
            return optional.get();
        } catch (Exception e) {
            return null;
        }
    }
}
