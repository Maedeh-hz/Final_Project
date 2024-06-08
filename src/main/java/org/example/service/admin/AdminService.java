package org.example.service.admin;

import org.example.base.service.BaseService;
import org.example.model.Admin;

public interface AdminService extends BaseService<Admin, Long> {
    Admin findByUsername(String username);
}
