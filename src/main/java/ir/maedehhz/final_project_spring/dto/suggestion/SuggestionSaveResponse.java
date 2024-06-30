package ir.maedehhz.final_project_spring.dto.suggestion;

import ir.maedehhz.final_project_spring.model.Expert;
import ir.maedehhz.final_project_spring.model.Order;

import java.time.LocalDate;

public record SuggestionSaveResponse(Expert expert, Order order,
                                     String description, LocalDate registerDate,
                                     Double price, Double workDuration) {
}
