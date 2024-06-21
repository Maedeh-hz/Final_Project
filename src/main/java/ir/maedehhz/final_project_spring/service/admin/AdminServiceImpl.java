package ir.maedehhz.final_project_spring.service.admin;

import ir.maedehhz.final_project_spring.exception.DuplicateInfoException;
import ir.maedehhz.final_project_spring.exception.NotFoundException;
import ir.maedehhz.final_project_spring.model.Admin;
import ir.maedehhz.final_project_spring.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository repository;

    @Override
    public Admin save(Admin admin) {
        if (repository.existsByUsername(admin.getUsername()))
            throw new DuplicateInfoException
                    (String.format("User with username %s exists!", admin.getUsername()));
        return repository.save(admin);
    }

    @Override
    public Admin findById(long id) {
        return repository.findById(id).orElseThrow(() ->
                new NotFoundException("Admin with id %s couldn't be found."));
    }
}
