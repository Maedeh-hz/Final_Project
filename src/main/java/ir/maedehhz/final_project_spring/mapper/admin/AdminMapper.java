package ir.maedehhz.final_project_spring.mapper.admin;

import ir.maedehhz.final_project_spring.dto.admin.AdminSaveRequest;
import ir.maedehhz.final_project_spring.dto.admin.AdminSaveResponse;
import ir.maedehhz.final_project_spring.model.Admin;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AdminMapper {
    AdminMapper INSTANCE = Mappers.getMapper(AdminMapper.class);

    Admin adminSaveRequestToModel(AdminSaveRequest request);
    AdminSaveResponse modelToAdminSaveResponse(Admin admin);
}
