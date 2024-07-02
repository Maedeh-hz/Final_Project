package ir.maedehhz.final_project_spring.service.service;

import ir.maedehhz.final_project_spring.model.Service;

import java.util.List;

public interface ServiceService {
    Service save(Service service);
    Service findById(long id);
    boolean existsByServiceName(String serviceName);
    List<Service> findAll();
}
