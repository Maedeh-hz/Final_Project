package org.example.service.user;

import org.example.base.exception.Exception;
import org.example.base.service.BaseServiceImpl;
import org.example.model.User;
import org.example.repository.user.UserRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.Optional;

public class UserServiceImpl extends BaseServiceImpl<User, Long, UserRepository>
        implements UserService {
    public UserServiceImpl(UserRepository repository, SessionFactory sessionFactory) {
        super(repository, sessionFactory);
    }

    @Override
    public String findTypeByUsername(String username) {
        try (Session session = sessionFactory.getCurrentSession()) {
            session.beginTransaction();
            Optional<String> optionalUser = repository.findTypeByUsername(username);
            optionalUser.orElseThrow(
                    () -> new Exception(String.format("User with username: %s not found",
                            username))
            );
            return optionalUser.get();
        } catch (Exception e) {
            return null;
        }
    }
}
