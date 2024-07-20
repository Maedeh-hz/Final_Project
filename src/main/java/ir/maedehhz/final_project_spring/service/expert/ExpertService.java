package ir.maedehhz.final_project_spring.service.expert;

import ir.maedehhz.final_project_spring.dto.expert.ExpertFilteringResponse;
import ir.maedehhz.final_project_spring.model.Expert;

import java.util.List;

public interface ExpertService {
    Expert save(Expert expert, String imagePath);

    Expert updateScore(Expert expert);

    Expert findByEmail(String email);

    void enableExpert(String username);

    Expert updatePassword(String newPass, String newPass2);

    Expert updateStatusToVerified(long expertId);

    Expert updateStatusToUnverified(long expertId);

    Expert findById(long id);

    List<ExpertFilteringResponse> filteringExperts(
            String firstName, String lastName, String email, Double score, String expertise,
            String registrationDate, String expertStatus
    );

}
