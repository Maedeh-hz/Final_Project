package ir.maedehhz.final_project_spring.service.user;

import ir.maedehhz.final_project_spring.exception.NotFoundException;
import ir.maedehhz.final_project_spring.model.User;
import ir.maedehhz.final_project_spring.repository.UserRepository;
 import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final EntityManager entityManager;

    @Override
    public User findByEmail(String email) {
        return repository.findByEmail(email).orElseThrow(() ->
                new NotFoundException(String.format("No user with email %s found!", email)));
    }

    @Override
    public User findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(
                String.format("User with id %s not found!", id)
        ));
    }

    @Override
    public List<User> filteringUsers(String dtype, String firstName, String lastName, String email, String registrationDate) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);

        Root<User> root = cq.from(User.class);
        List<Predicate> predicates = new ArrayList<>();

        if (dtype != null)
            predicates.add(cb.equal(root.get("dtype"), dtype));

        if (firstName != null )
            predicates.add(cb.like(root.get("firstName"), "%" + firstName + "%"));

        if (lastName != null)
            predicates.add(cb.like(root.get("lastName"), "%" + lastName + "%"));

        if (email != null)
            predicates.add(cb.like(root.get("email"), "%" + email + "%"));

        if (registrationDate != null)
            predicates.add(cb.like(root.get("registrationDate"), "%" + registrationDate + "%"));

        cq.where(predicates.toArray(new Predicate[0]));
        return entityManager.createQuery(cq).getResultList();
    }
}
