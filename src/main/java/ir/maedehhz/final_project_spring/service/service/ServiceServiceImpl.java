package ir.maedehhz.final_project_spring.service.service;

import ir.maedehhz.final_project_spring.exception.DuplicateInfoException;
import ir.maedehhz.final_project_spring.exception.NotFoundException;
import ir.maedehhz.final_project_spring.model.Service;
import ir.maedehhz.final_project_spring.model.Subservice;
import ir.maedehhz.final_project_spring.repository.ServiceRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ServiceServiceImpl implements ServiceService{

    private final ServiceRepository repository;

    @Override
    public Service save(Service service) {
        if (existsByServiceName(service.getServiceName()))
            throw new DuplicateInfoException(String.format("Service with name %s exists!", service.getServiceName()));
        return repository.save(service);
    }

    @Override
    public Service findById(long id) {
        return repository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("Service with id %s couldn't be found.", id)));
    }

    @Override
    public boolean existsByServiceName(String serviceName) {
        return repository.existsByServiceName(serviceName);
    }

    @Override
    public List<Service> findAll() {
        return repository.findAll();
    }
}
