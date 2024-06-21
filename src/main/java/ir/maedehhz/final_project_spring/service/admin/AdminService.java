package ir.maedehhz.final_project_spring.service.admin;

import ir.maedehhz.final_project_spring.model.Admin;

public interface AdminService {
    Admin save(Admin admin);
    Admin findById(long id);
}
