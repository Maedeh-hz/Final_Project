package ir.maedehhz.final_project_spring.dto.expert;

import ir.maedehhz.final_project_spring.model.enums.ExpertStatus;

import java.time.LocalDateTime;

public record ExpertFilteringResponse (long id, String firstName, String lastName,
                                       String expertise, ExpertStatus status,
                                       Double score, LocalDateTime registrationDate) {
}
