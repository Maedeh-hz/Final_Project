package ir.maedehhz.final_project_spring.mapper.expert;

import ir.maedehhz.final_project_spring.dto.expert.ExpertSaveRequest;
import ir.maedehhz.final_project_spring.dto.expert.ExpertSaveResponse;
import ir.maedehhz.final_project_spring.model.Expert;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ExpertMapper {
    ExpertMapper INSTANCE = Mappers.getMapper(ExpertMapper.class);

    Expert expertSaveRequestToModel(ExpertSaveRequest request);
    ExpertSaveResponse modelToExpertSaveResponse(Expert expert);
}
