package ir.maedehhz.final_project_spring.service.subservice;

import ir.maedehhz.final_project_spring.model.Subservice;

import java.util.List;

public interface SubserviceService {
    Subservice save(Subservice subservice);
    Subservice findById(long id);
    Subservice updateDescription(long id, String newDescription);
    Subservice updateBasePrice(long id, double newBasePrice);
    List<Subservice> findAll();
}
