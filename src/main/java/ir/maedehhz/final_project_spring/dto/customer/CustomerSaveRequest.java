package ir.maedehhz.final_project_spring.dto.customer;

public record CustomerSaveRequest(String firstName, String lastName,
                                  String email, String password) {
}
