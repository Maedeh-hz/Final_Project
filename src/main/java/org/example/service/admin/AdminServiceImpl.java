package org.example.service.admin;

import org.example.base.exception.Exception;
import org.example.base.service.BaseServiceImpl;
import org.example.model.Admin;
import org.example.model.Customer;
import org.example.repository.admin.AdminRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Optional;

public class AdminServiceImpl extends BaseServiceImpl<Admin, Long, AdminRepository>
        implements AdminService {
    public AdminServiceImpl(AdminRepository repository, SessionFactory sessionFactory) {
        super(repository, sessionFactory);
    }

    @Override
    public Admin findByUsername(String username) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Optional<Admin> optional = repository.findByUsername(username);
            optional.orElseThrow(
                    () -> new Exception(String.format("User with username: %s not found",
                            username))
            );
            return optional.get();
        } catch (Exception e) {
            return null;
        }
    }
}
