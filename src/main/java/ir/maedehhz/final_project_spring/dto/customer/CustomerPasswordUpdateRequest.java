package ir.maedehhz.final_project_spring.dto.customer;

public record CustomerPasswordUpdateRequest(long userId, String newPass, String newPass2) {
}
