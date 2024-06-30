package ir.maedehhz.final_project_spring.dto.suggestion;

public record SuggestionSaveRequest(long expertId,
                                    long orderId, String description,
                                    Double price, Double workDuration) {
}
