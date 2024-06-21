package ir.maedehhz.final_project_spring.repository;

import ir.maedehhz.final_project_spring.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
