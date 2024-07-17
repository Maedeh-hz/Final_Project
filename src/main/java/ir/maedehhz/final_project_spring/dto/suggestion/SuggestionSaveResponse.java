package ir.maedehhz.final_project_spring.dto.suggestion;

import java.time.LocalDate;

public record SuggestionSaveResponse(Long expertId,
                                     String description, LocalDate registerDate,
                                     Double price, Double workDuration) {
}
