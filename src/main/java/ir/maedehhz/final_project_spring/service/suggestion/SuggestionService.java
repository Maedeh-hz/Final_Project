package ir.maedehhz.final_project_spring.service.suggestion;

import ir.maedehhz.final_project_spring.model.Order;
import ir.maedehhz.final_project_spring.model.Suggestion;

import java.util.List;

public interface SuggestionService {

    Suggestion registerSuggestionForOrder(Suggestion suggestion, long expertId, long orderId);

    Order choosingExpert(long suggestionId);

    List<Suggestion> viewAllByExpertScore(long orderId);

    List<Suggestion> viewAllByPrice(long orderId);

    Suggestion findById(long id);

}
