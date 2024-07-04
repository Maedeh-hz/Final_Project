package ir.maedehhz.final_project_spring.repository;

import ir.maedehhz.final_project_spring.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
}
