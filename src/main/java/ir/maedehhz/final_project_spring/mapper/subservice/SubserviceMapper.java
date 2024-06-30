package ir.maedehhz.final_project_spring.mapper.subservice;

import ir.maedehhz.final_project_spring.dto.subservice.SubserviceSaveRequest;
import ir.maedehhz.final_project_spring.dto.subservice.SubserviceSaveResponse;
import ir.maedehhz.final_project_spring.model.Subservice;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SubserviceMapper {
    SubserviceMapper INSTANCE = Mappers.getMapper(SubserviceMapper.class);
    Subservice subserviceSaveRequestToModel(SubserviceSaveRequest request);
    SubserviceSaveResponse modelToSubserviceSaveResponse(Subservice subservice);
}
