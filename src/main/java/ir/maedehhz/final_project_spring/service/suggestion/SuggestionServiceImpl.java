package ir.maedehhz.final_project_spring.service.suggestion;

import ir.maedehhz.final_project_spring.exception.NotYourBusinessException;
import ir.maedehhz.final_project_spring.exception.PriceUnderRangeException;
import ir.maedehhz.final_project_spring.model.Subservice;
import ir.maedehhz.final_project_spring.model.Suggestion;
import ir.maedehhz.final_project_spring.model.User_SubService;
import ir.maedehhz.final_project_spring.model.enums.OrderStatus;
import ir.maedehhz.final_project_spring.repository.SuggestionRepository;
import ir.maedehhz.final_project_spring.service.user_subservice.User_SubserviceServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SuggestionServiceImpl implements SuggestionService{

    private final SuggestionRepository repository;

    private final User_SubserviceServiceImpl user_subserviceService;

    @Override
    public Suggestion save(Suggestion suggestion) {
        List<Subservice> subservice = getAllSubserviceOfExpert(suggestion.getExpert().getId());
        List<Long> subserviceIds = new ArrayList<>();
        for (Subservice sub : subservice) {
            subserviceIds.add(sub.getId());
        }

        if (!subserviceIds.contains(suggestion.getOrder().getSubservice().getId()))
            throw new NotYourBusinessException("You are not under service of this orders Subservice.");

        if (!suggestion.getOrder().getStatus().equals(OrderStatus.WAITING_FOR_EXPERT_SUGGESTION))
            throw new NotYourBusinessException("Order is not waiting for any suggestions!");

        if (suggestion.getPrice() < suggestion.getOrder().getSubservice().getBasePrice())
            throw new PriceUnderRangeException("Suggesting price from expert is less than Subservice base price!");

        return repository.save(suggestion);
    }

    private List<Subservice> getAllSubserviceOfExpert(long expertId){
        List<User_SubService> allByExpertId = user_subserviceService.findAllByExpert_Id(expertId);
        List<Subservice> subservice = new ArrayList<>();
        for (User_SubService userSubService : allByExpertId) {
            subservice.add(userSubService.getSubservice());
        }

        return subservice;
    }
}
