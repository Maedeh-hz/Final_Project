package ir.maedehhz.final_project_spring.service.user_subservice;

import ir.maedehhz.final_project_spring.model.Expert;
import ir.maedehhz.final_project_spring.model.Subservice;
import ir.maedehhz.final_project_spring.model.User_SubService;

import java.util.List;

public interface User_SubserviceService {
    User_SubService save(long expertId, long subserviceId);
    boolean remove(long subserviceId, long expertId);
    List<Subservice> findAllByExpert_Id(long expertId);
}
