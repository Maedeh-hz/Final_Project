package ir.maedehhz.final_project_spring.mapper.service;

import ir.maedehhz.final_project_spring.dto.service.ServiceSaveRequest;
import ir.maedehhz.final_project_spring.dto.service.ServiceSaveResponse;
import ir.maedehhz.final_project_spring.model.Service;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ServiceMapper {
    ServiceMapper INSTANCE = Mappers.getMapper(ServiceMapper.class);
    Service serviceSaveRequestToModel(ServiceSaveRequest request);
    ServiceSaveResponse modelToServiceSaveResponse(Service service);
}
