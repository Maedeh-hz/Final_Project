package ir.maedehhz.final_project_spring.dto.expert;

import ir.maedehhz.final_project_spring.model.Wallet;
import ir.maedehhz.final_project_spring.model.enums.ExpertStatus;

public record ExpertSaveResponse(long id, String firstName, String lastName,
                                 String expertise,
                                 String email, String username,
                                 Wallet wallet, ExpertStatus status,
                                 Double score, byte[] image) {
}
