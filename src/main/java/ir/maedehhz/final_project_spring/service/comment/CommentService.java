package ir.maedehhz.final_project_spring.service.comment;

import ir.maedehhz.final_project_spring.model.Comment;
import ir.maedehhz.final_project_spring.model.Order;

public interface CommentService {
    Comment save(Comment comment, long orderId);
    Comment findByOrder(Order order);
    double viewExpertsScoreByOrder(long orderId);
}
