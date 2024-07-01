package ir.maedehhz.final_project_spring.service.suggestion;

import ir.maedehhz.final_project_spring.exception.*;
import ir.maedehhz.final_project_spring.model.Expert;
import ir.maedehhz.final_project_spring.model.Order;
import ir.maedehhz.final_project_spring.model.Subservice;
import ir.maedehhz.final_project_spring.model.Suggestion;
import ir.maedehhz.final_project_spring.model.enums.OrderStatus;
import ir.maedehhz.final_project_spring.repository.SuggestionRepository;
import ir.maedehhz.final_project_spring.service.expert.ExpertServiceImpl;
import ir.maedehhz.final_project_spring.service.order.OrderServiceImpl;
import ir.maedehhz.final_project_spring.service.user_subservice.User_SubserviceServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SuggestionServiceImpl implements SuggestionService{

    private final SuggestionRepository repository;

    private final OrderServiceImpl orderService;

    private final ExpertServiceImpl expertService;

    private final User_SubserviceServiceImpl user_subserviceService;

    @Override
    public Suggestion registerSuggestionForOrder(Suggestion suggestion, long expertId, long orderId) {
        Expert expert = expertService.findById(expertId);
        Order order = orderService.findById(orderId);

        suggestion.setExpert(expert);
        suggestion.setOrder(order);
        suggestion.setRegisterDate(LocalDate.now());

        List<Subservice> subservice = user_subserviceService.findAllByExpert_Id(expert.getId());
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

        if (suggestion.getRegisterDate().isBefore(LocalDate.now()))
            throw new DateMismatchException("The entrance Date is invalid!");

        return repository.save(suggestion);
    }

    @Override
    public Order choosingExpert(long suggestionId) {
        Order order = updatingOrdersSuggestion(suggestionId);

        if (!order.getSuggestion().getId().equals(suggestionId))
            throw new CouldNotUpdateException("couldn't register the suggestion for order!");

        return orderService.updateStatusToWaitingForExpertToVisit(order.getId());
    }

    private Order updatingOrdersSuggestion(long suggestionId){
        Suggestion suggestion = findById(suggestionId);

        Order orderById = orderService.findById(suggestion.getOrder().getId());

        orderById.setSuggestion(suggestion);

        return orderService.updatingOrderSuggestion(orderById);
    }

    @Override
    public List<Suggestion> viewAllByExpertScore() {
        if (repository.findAll().isEmpty())
            throw new NotFoundException("No Suggestions found.");

        return repository.findAllByExpert_Score();
    }

    @Override
    public List<Suggestion> viewAllByPrice() {
        if (repository.findAll().isEmpty())
            throw new NotFoundException("No Suggestions found.");

        return repository.findAllByPrice();
    }

    @Override
    public Suggestion findById(long id) {
        return repository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("Suggestion with id %s couldn't be found.", id)));
    }

    @Override
    public Suggestion findByExpertAndOrder(Expert expert, Order order) {
        if (!repository.existsByExpertAndOrder(expert, order))
            throw new NotFoundException("Noting found!");
        return repository.findByExpertAndOrder(expert, order);
    }
}
