package ir.maedehhz.final_project_spring.service.user;

import ir.maedehhz.final_project_spring.model.User;

import java.util.List;

public interface UserService {
    User findByUsername(String username);

    List<User> filteringUsers(
            String dtype, String firstName, String lastName, String email
    );
}
