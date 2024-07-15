package ir.maedehhz.final_project_spring.dto.expert;

import ir.maedehhz.final_project_spring.model.enums.ExpertStatus;

public record ExpertFilteringResponse (long id, String firstName, String lastName,
                                       String expertise, String username,
                                       ExpertStatus status, Double score) {
}
