package ir.maedehhz.final_project_spring.dto.comment;

import ir.maedehhz.final_project_spring.model.Order;

import java.time.LocalDateTime;

public record CommentSaveResponse (long id ,String context, Double expertScore,
                                   LocalDateTime registrationDate, Order order) {
}
