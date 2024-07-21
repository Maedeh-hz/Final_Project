package ir.maedehhz.final_project_spring.service.suggestion;

import ir.maedehhz.final_project_spring.dto.suggestion.SuggestionFindAllResponse;
import ir.maedehhz.final_project_spring.exception.DateMismatchException;
import ir.maedehhz.final_project_spring.exception.InvalidInputException;
import ir.maedehhz.final_project_spring.exception.InvalidRequestException;
import ir.maedehhz.final_project_spring.exception.NotFoundException;
import ir.maedehhz.final_project_spring.mapper.suggestion.SuggestionMapper;
import ir.maedehhz.final_project_spring.model.Expert;
import ir.maedehhz.final_project_spring.model.Order;
import ir.maedehhz.final_project_spring.model.Subservice;
import ir.maedehhz.final_project_spring.model.Suggestion;
import ir.maedehhz.final_project_spring.model.enums.OrderStatus;
import ir.maedehhz.final_project_spring.repository.SuggestionRepository;
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

    private final User_SubserviceServiceImpl user_subserviceService;

    @Override
    public Suggestion registerSuggestionForOrder(Suggestion suggestion, Expert expert, Order order) {
        suggestion.setExpert(expert);
        suggestion.setOrder(order);
        suggestion.setRegisterDate(LocalDate.now());

        List<Subservice> subservice = user_subserviceService.findAllByExpert_Id(expert.getId());
        List<Long> subserviceIds = new ArrayList<>();
        for (Subservice sub : subservice) {
            subserviceIds.add(sub.getId());
        }

        if (!subserviceIds.contains(suggestion.getOrder().getSubservice().getId()))
            throw new InvalidRequestException("You are not under service of this orders Subservice.");

        if (!suggestion.getOrder().getStatus().equals(OrderStatus.WAITING_FOR_EXPERT_SUGGESTION))
            throw new InvalidRequestException("Order is not waiting for any suggestions!");

        if (suggestion.getPrice() < suggestion.getOrder().getSubservice().getBasePrice())
            throw new InvalidInputException("Suggesting price from expert is less than Subservice base price!");

        if (suggestion.getRegisterDate().isBefore(LocalDate.now()))
            throw new DateMismatchException("The entrance Date is invalid!");

        return repository.save(suggestion);
    }

    @Override
    public List<Suggestion> viewAllByExpertScore(Order order) {
        if (repository.findAll().isEmpty())
            throw new NotFoundException("No Suggestions found.");

        return repository.findAllByExpert_ScoreAndOrder(order);
    }

    @Override
    public List<Suggestion> viewAllByPrice(Order order) {
        if (repository.findAll().isEmpty())
            throw new NotFoundException("No Suggestions found.");

        return repository.findAllByPriceAndOrder(order);
    }

    @Override
    public Suggestion findById(long id) {
        return repository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("Suggestion with id %s couldn't be found.", id)));
    }

    @Override
    public void confirmSuggestionAcceptance(Long suggestionId) {
        Suggestion suggestion = findById(suggestionId);
        suggestion.setAccepted(true);
        repository.save(suggestion);
    }

    @Override
    public List<SuggestionFindAllResponse> findAllByExpertId(Long expertId) {
        List<Suggestion> all = repository.findAllByExpert_Id(expertId);
        if (all.isEmpty())
            throw new NotFoundException("No suggestions found!");

        List<SuggestionFindAllResponse> responses = new ArrayList<>();
        all.forEach(suggestion -> responses.add(SuggestionMapper.INSTANCE.modelToSuggestionFindAllResponse(suggestion)));
        return responses;
    }

    @Override
    public Suggestion findByExpertAndOrder(Expert expert, Order order) {
        return repository.findByExpertAndOrder(expert, order)
                .orElseThrow(() -> new NotFoundException("No Suggestions founded!"));
    }

}
