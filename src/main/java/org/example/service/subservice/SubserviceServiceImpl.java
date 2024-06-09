package org.example.service.subservice;

import org.example.base.exception.Exception;
import org.example.base.service.BaseServiceImpl;
import org.example.exception.NoResultException;
import org.example.model.Expert;
import org.example.model.Service;
import org.example.model.Subservice;
import org.example.repository.subservice.SubserviceRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class SubserviceServiceImpl extends BaseServiceImpl<Subservice, Long, SubserviceRepository>
        implements Sub_serviceService {
    public SubserviceServiceImpl(SubserviceRepository repository, SessionFactory sessionFactory) {
        super(repository, sessionFactory);
    }

    @Override
    public List<Subservice> loadAll() {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Optional<List<Subservice>> optional = repository.loadAll();
            optional.orElseThrow(
                    () -> new NoResultException("No Subservice founded."));
            return optional.get();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Subservice> loadAllByService(Service service) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Optional<List<Subservice>> optional = repository.loadAllByService(service);
            optional.orElseThrow(
                    () -> new NoResultException("No Subservice founded."));
            return optional.get();
        } catch (Exception e) {
            return null;
        }
    }


}
