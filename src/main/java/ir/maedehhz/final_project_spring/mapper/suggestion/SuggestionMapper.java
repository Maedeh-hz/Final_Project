package ir.maedehhz.final_project_spring.mapper.suggestion;

import ir.maedehhz.final_project_spring.dto.suggestion.SuggestionFindAllResponse;
import ir.maedehhz.final_project_spring.dto.suggestion.SuggestionSaveRequest;
import ir.maedehhz.final_project_spring.dto.suggestion.SuggestionSaveResponse;
import ir.maedehhz.final_project_spring.model.Suggestion;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SuggestionMapper {
    SuggestionMapper INSTANCE = Mappers.getMapper(SuggestionMapper.class);

    Suggestion suggestionSaveRequestToModel(SuggestionSaveRequest request);
    SuggestionSaveResponse modelToSuggestionSaveResponse(Suggestion suggestion);
    SuggestionFindAllResponse modelToSuggestionFindAllResponse(Suggestion suggestion);
}
