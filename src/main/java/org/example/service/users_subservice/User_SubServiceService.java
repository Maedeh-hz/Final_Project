package org.example.service.users_subservice;

import org.example.base.service.BaseService;
import org.example.model.Expert;
import org.example.model.Subservice;
import org.example.model.User_SubService;

import java.util.List;

public interface User_SubServiceService extends BaseService<User_SubService, Long> {
    List<User_SubService> findByExpert(Expert expert);
    List<User_SubService> findBySubservice(Subservice subservice);
    User_SubService findByExpertAndSubservice(Expert expert, Subservice subservice);
}
