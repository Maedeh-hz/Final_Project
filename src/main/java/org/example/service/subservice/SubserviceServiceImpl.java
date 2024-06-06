package org.example.service.subservice;

import org.example.base.service.BaseServiceImpl;
import org.example.exception.UnverifiedExpertException;
import org.example.model.Subservice;
import org.example.model.enums.ExpertsLevel;
import org.example.repository.subservice.SubserviceRepository;
import org.hibernate.SessionFactory;

public class SubserviceServiceImpl extends BaseServiceImpl<Subservice, Long, SubserviceRepository>
        implements SubserviceService {
    public SubserviceServiceImpl(SubserviceRepository repository, SessionFactory sessionFactory) {
        super(repository, sessionFactory);
    }

    @Override
    public Subservice save(Subservice subservice) {
        if (subservice.getExpert().getExpertsLevel().equals(ExpertsLevel.VERIFIED)){
            return saveOrUpdate(subservice);
        } else
            throw new UnverifiedExpertException(String.format("Expert with id: %s is unverified.",
                    subservice.getExpert().getId()));
    }
}
