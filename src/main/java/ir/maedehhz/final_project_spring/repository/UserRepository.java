package ir.maedehhz.final_project_spring.repository;

import ir.maedehhz.final_project_spring.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> , JpaSpecificationExecutor<User> {
    List<User> findAllByDtype(String dtype);

    List<User> findAllByFirstNameLike(String firstName);

    List<User> findAllByLastNameLike(String lastName);

    List<User> findAllByEmailLike(String email);
}
