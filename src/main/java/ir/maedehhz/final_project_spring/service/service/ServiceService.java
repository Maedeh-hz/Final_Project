package ir.maedehhz.final_project_spring.service.service;

import ir.maedehhz.final_project_spring.model.Service;

public interface ServiceService {
    Service save(Service service);
    Service findById(long id);
    boolean existsByServiceName(String serviceName);
}
