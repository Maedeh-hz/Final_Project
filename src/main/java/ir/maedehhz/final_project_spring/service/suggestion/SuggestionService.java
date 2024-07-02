package ir.maedehhz.final_project_spring.service.suggestion;

import ir.maedehhz.final_project_spring.model.Expert;
import ir.maedehhz.final_project_spring.model.Order;
import ir.maedehhz.final_project_spring.model.Suggestion;

import java.util.List;

public interface SuggestionService {

    Suggestion registerSuggestionForOrder(Suggestion suggestion, long expertId, long orderId);

    Order choosingExpert(long suggestionId);

    List<Suggestion> viewAllByExpertScore();

    List<Suggestion> viewAllByPrice();

    Suggestion findById(long id);

}
