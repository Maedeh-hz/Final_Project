package ir.maedehhz.final_project_spring.repository;

import ir.maedehhz.final_project_spring.model.Expert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExpertRepository extends JpaRepository<Expert, Long>, JpaSpecificationExecutor<Expert> {

    boolean existsByEmail(String email);

    Optional<Expert> findByEmail(String email);
}
