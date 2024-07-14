package ir.maedehhz.final_project_spring.service.expert;

import ir.maedehhz.final_project_spring.model.Expert;

public interface ExpertService {
    Expert save(Expert expert, String imagePath);

    Expert updateScore(Expert expert);

    Expert findByUsername(String username);

    void enableExpert(String username);

    Expert updatePassword(long expertId, String previousPass, String newPass, String newPass2);

    Expert updateStatusToVerified(long expertId);

    Expert updateStatusToUnverified(long expertId);

    Expert findById(long id);

}
