package ir.maedehhz.final_project_spring.repository;

import ir.maedehhz.final_project_spring.model.Order;
import ir.maedehhz.final_project_spring.model.Suggestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SuggestionRepository extends JpaRepository<Suggestion, Long> {
    @Query("select s from Suggestion s where s.order = ?1 order by s.expert.score desc ")
    List<Suggestion> findAllByExpert_ScoreAndOrder(Order order);

    @Query("select s from Suggestion s where s.order = ?1 order by s.price asc ")
    List<Suggestion> findAllByPriceAndOrder(Order order);
}
