package ir.maedehhz.final_project_spring.dto.expert;

public record ExpertPasswordUpdateRequest(long userId, String newPass, String newPass2) {
}
