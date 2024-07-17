package ir.maedehhz.final_project_spring.dto.comment;

import ir.maedehhz.final_project_spring.dto.order.OrderFindAllResponse;

import java.time.LocalDateTime;

public record CommentSaveResponse (long id ,String context, Double expertScore,
                                   LocalDateTime registrationDate, OrderFindAllResponse order) {
}
