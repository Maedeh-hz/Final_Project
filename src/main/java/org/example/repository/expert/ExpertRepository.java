package org.example.repository.expert;

import org.example.base.repository.BaseRepository;
import org.example.model.Expert;

import java.util.List;
import java.util.Optional;

public interface ExpertRepository extends BaseRepository<Expert, Long> {
    Optional<Expert> findByUsername(String username);

    Optional<List<Expert>> loadAllWaitingForVerificationExperts();

    Optional<List<Expert>> loadAllVerifiedExperts();
}
