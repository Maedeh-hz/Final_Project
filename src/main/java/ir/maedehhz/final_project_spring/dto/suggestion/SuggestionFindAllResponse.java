package ir.maedehhz.final_project_spring.dto.suggestion;

import ir.maedehhz.final_project_spring.dto.expert.ExpertSaveResponse;

import java.time.LocalDate;

public record SuggestionFindAllResponse(ExpertSaveResponse expert,
                                        String description, LocalDate registerDate,
                                        Double price, Double workDuration, Boolean accepted) {
}
