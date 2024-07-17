package ir.maedehhz.final_project_spring.service.user_subservice;

import ir.maedehhz.final_project_spring.exception.DuplicateInfoException;
import ir.maedehhz.final_project_spring.exception.NotFoundException;
import ir.maedehhz.final_project_spring.exception.UnverifiedExpertException;
import ir.maedehhz.final_project_spring.model.Expert;
import ir.maedehhz.final_project_spring.model.Subservice;
import ir.maedehhz.final_project_spring.model.User_SubService;
import ir.maedehhz.final_project_spring.model.enums.ExpertStatus;
import ir.maedehhz.final_project_spring.repository.User_SubserviceRepository;
import ir.maedehhz.final_project_spring.service.expert.ExpertServiceImpl;
import ir.maedehhz.final_project_spring.service.subservice.SubserviceServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class User_SubserviceServiceImpl implements User_SubserviceService{
    private final User_SubserviceRepository repository;
    private final SubserviceServiceImpl subserviceService;
    private final ExpertServiceImpl expertService;

    @Override
    public User_SubService save(long expertId, long subserviceId) {
        Expert expert = expertService.findById(expertId);
        Subservice subservice = subserviceService.findById(subserviceId);

        User_SubService model = User_SubService.builder()
                .subservice(subservice).expert(expert).build();

        if (repository.existsByExpertAndSubservice(model.getExpert(), model.getSubservice()))
            throw new DuplicateInfoException(
                    "Expert has been added to this Subservice before!");

        if (model.getExpert().getStatus().equals(ExpertStatus.UNVERIFIED))
            throw new UnverifiedExpertException("Expert is not verified!");

        return repository.save(model);
    }

    @Override
    public void remove(long subserviceId, long expertId) {
        User_SubService founded = repository.findByExpert_IdAndAndSubservice_Id(expertId, subserviceId)
                .orElseThrow(()-> new NotFoundException(String.format(
                        "Expert with id %s is not under service of this Subservice!", expertId))
                );
        repository.delete(founded);
    }

    @Override
    public List<Subservice> findAllByExpert_Id(long expertId) {
        List<Subservice> subservices = new ArrayList<>();
        List<User_SubService> all = repository.findAllByExpert_Id(expertId);
        if (all.isEmpty())
            throw new NotFoundException("No subservices found!");

        all.forEach(userSubService -> subservices.add(userSubService.getSubservice()));

        return subservices;
    }

}
