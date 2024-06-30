package ir.maedehhz.final_project_spring.repository;

import ir.maedehhz.final_project_spring.model.Expert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpertRepository extends JpaRepository<Expert, Long> {
    boolean existsByUsername(String username);

    Expert findByUsername(String username);
}
