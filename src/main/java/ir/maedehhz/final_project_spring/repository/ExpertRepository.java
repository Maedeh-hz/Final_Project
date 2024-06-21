package ir.maedehhz.final_project_spring.repository;

import ir.maedehhz.final_project_spring.model.Expert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExpertRepository extends JpaRepository<Expert, Long> {
    <S extends Expert> boolean existsByUsername(String username);

    <S extends Expert, R> R findByUsername(String username);

    @Override
    Optional<Expert> findById(Long aLong);
}
