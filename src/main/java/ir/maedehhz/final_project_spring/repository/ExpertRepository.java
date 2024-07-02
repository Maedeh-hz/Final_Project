package ir.maedehhz.final_project_spring.repository;

import ir.maedehhz.final_project_spring.model.Expert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpertRepository extends JpaRepository<Expert, Long>, JpaSpecificationExecutor<Expert> {
    boolean existsByUsername(String username);

    Expert findByUsername(String username);

    List<Expert> findAllByExpertiseLike(String expertise);

    List<Expert> findAllByScore(Double score);
}
