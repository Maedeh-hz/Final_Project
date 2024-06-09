package org.example.repository.users_subservice;

import org.example.base.repository.BaseRepository;
import org.example.model.Expert;
import org.example.model.Subservice;
import org.example.model.User_SubService;

import java.util.List;
import java.util.Optional;

public interface User_SubServiceRepository extends BaseRepository<User_SubService, Long> {
    Optional<List<User_SubService>> findByExpert(Expert expert);
    Optional<List<User_SubService>> findBySubservice(Subservice subservice);
    Optional<User_SubService> findByExpertAndSubservice(Expert expert, Subservice subservice);
}
