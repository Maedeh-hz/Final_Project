package ir.maedehhz.final_project_spring.service.admin;

import ir.maedehhz.final_project_spring.exception.DuplicateInfoException;
import ir.maedehhz.final_project_spring.exception.NotFoundException;
import ir.maedehhz.final_project_spring.model.Admin;
import ir.maedehhz.final_project_spring.model.enums.Role;
import ir.maedehhz.final_project_spring.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public Admin save(Admin admin) {
        if (repository.existsByEmail(admin.getEmail()))
            throw new DuplicateInfoException
                    (String.format("User with email %s exists!", admin.getEmail()));
        admin.setRegistrationDate(LocalDateTime.now());
        admin.setRole(Role.ROLE_ADMIN);
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        return repository.save(admin);
    }

    @Override
    public Admin findById(long id) {
        return repository.findById(id).orElseThrow(() ->
                new NotFoundException("Admin with id %s couldn't be found."));
    }
}
