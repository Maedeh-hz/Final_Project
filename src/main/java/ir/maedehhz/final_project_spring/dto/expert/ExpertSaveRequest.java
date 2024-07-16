package ir.maedehhz.final_project_spring.dto.expert;

public record ExpertSaveRequest(String firstName, String lastName,
                                String expertise,
                                String email, String password, String imagePath) {
}
