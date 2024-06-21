package ir.maedehhz.final_project_spring.service.expert;

import ir.maedehhz.final_project_spring.model.Expert;

public interface ExpertService {
    Expert save(Expert expert, String imagePath);

    Expert findByUsername(String username);

    Expert updatePassword(Expert expert, String newPass, String newPass2);

    Expert updateStatusToVerified(long expertId);

    Expert updateStatusToUnverified(long expertId);

    Expert findById(long id);
}
