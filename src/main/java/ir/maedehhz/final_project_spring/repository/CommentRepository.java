package ir.maedehhz.final_project_spring.repository;

import ir.maedehhz.final_project_spring.model.Comment;
import ir.maedehhz.final_project_spring.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByOrder(Order order);
    boolean existsByOrder(Order order);
}
