package ir.maedehhz.final_project_spring.service.user;

import ir.maedehhz.final_project_spring.exception.NotFoundException;
import ir.maedehhz.final_project_spring.model.User;
import ir.maedehhz.final_project_spring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Override
    public User findByUsername(String username) {
        return repository.findByUsername(username).orElseThrow(() ->
                new NotFoundException(String.format("No user with username %s found!", username)));
    }
}
