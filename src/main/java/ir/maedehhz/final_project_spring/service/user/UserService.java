package ir.maedehhz.final_project_spring.service.user;

import ir.maedehhz.final_project_spring.model.User;

public interface UserService {
    User findByUsername(String username);
}
