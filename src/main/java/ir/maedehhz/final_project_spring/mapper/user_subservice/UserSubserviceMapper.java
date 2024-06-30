package ir.maedehhz.final_project_spring.mapper.user_subservice;

import ir.maedehhz.final_project_spring.model.Expert;
import ir.maedehhz.final_project_spring.model.Subservice;
import ir.maedehhz.final_project_spring.model.User_SubService;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserSubserviceMapper {
    UserSubserviceMapper INSTANCE = Mappers.getMapper(UserSubserviceMapper.class);

    User_SubService usSaveRequestToModel(Subservice subservice, Expert expert);
}
