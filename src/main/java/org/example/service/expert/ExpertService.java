package org.example.service.expert;

import org.example.base.service.BaseService;
import org.example.model.Expert;

import java.util.List;
import java.util.Optional;

public interface ExpertService extends BaseService<Expert, Long> {
    Expert findByUsername(String username);

    List<Expert> loadAllWaitingForVerificationExperts();

    List<Expert> loadAllVerifiedExperts();
}
