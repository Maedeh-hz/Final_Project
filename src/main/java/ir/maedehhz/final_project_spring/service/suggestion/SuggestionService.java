package ir.maedehhz.final_project_spring.service.suggestion;

import ir.maedehhz.final_project_spring.dto.suggestion.SuggestionFindAllResponse;
import ir.maedehhz.final_project_spring.model.Expert;
import ir.maedehhz.final_project_spring.model.Order;
import ir.maedehhz.final_project_spring.model.Suggestion;

import java.util.List;

public interface SuggestionService {

    Suggestion registerSuggestionForOrder(Suggestion suggestion, Expert expert, Order order);

    List<Suggestion> viewAllByExpertScore(Order order);

    List<Suggestion> viewAllByPrice(Order order);

    Suggestion findById(long id);

    void confirmSuggestionAcceptance(Long suggestionId);

    List<SuggestionFindAllResponse> findAllByExpertId(Long expertId);

    Suggestion findByExpertAndOrder(Expert expert, Order order);

}
