package ir.maedehhz.final_project_spring.dto.expert;

import ir.maedehhz.final_project_spring.model.Wallet;

public record ExpertSaveRequest(String firstName, String lastName,
                                String expertise,
                                String email, String password,
                                Wallet wallet, String imagePath) {
}
