package org.example.service.user;

import org.example.base.service.BaseService;
import org.example.model.User;

public interface UserService extends BaseService<User, Long> {
    String findTypeByUsername(String username);
}
