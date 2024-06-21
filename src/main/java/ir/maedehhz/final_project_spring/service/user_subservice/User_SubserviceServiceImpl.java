package ir.maedehhz.final_project_spring.service.user_subservice;

import ir.maedehhz.final_project_spring.exception.DuplicateInfoException;
import ir.maedehhz.final_project_spring.exception.NotFoundException;
import ir.maedehhz.final_project_spring.exception.UnverifiedExpertException;
import ir.maedehhz.final_project_spring.model.User_SubService;
import ir.maedehhz.final_project_spring.model.enums.ExpertStatus;
import ir.maedehhz.final_project_spring.repository.User_SubserviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class User_SubserviceServiceImpl implements User_SubserviceService{
    private final User_SubserviceRepository repository;

    @Override
    public User_SubService save(User_SubService userSubService) {
        if (repository.existsByExpertAndSubservice(userSubService.getExpert(), userSubService.getSubservice()))
            throw new DuplicateInfoException(
                    "Expert has been added to this Subservice before!");

        if (userSubService.getExpert().getStatus().equals(ExpertStatus.UNVERIFIED))
            throw new UnverifiedExpertException("Expert is not verified!");

        return repository.save(userSubService);
    }

    @Override
    public boolean remove(long subserviceId, long expertId) {
        User_SubService founded = repository.findByExpert_IdAndAndSubservice_Id(expertId, subserviceId)
                .orElseThrow(()-> new NotFoundException(String.format(
                        "Expert with id %s is not under service of this Subservice!", expertId))
                );
        repository.delete(founded);
        return true;
    }

    @Override
    public List<User_SubService> findAllByExpert_Id(long expertId) {
        return repository.findAllByExpert_Id(expertId);
    }

}
