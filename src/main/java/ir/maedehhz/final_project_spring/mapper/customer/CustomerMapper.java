package ir.maedehhz.final_project_spring.mapper.customer;

import ir.maedehhz.final_project_spring.dto.customer.CustomerSaveRequest;
import ir.maedehhz.final_project_spring.dto.customer.CustomerSaveResponse;
import ir.maedehhz.final_project_spring.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CustomerMapper {
    
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    Customer customerSaveRequestToModel(CustomerSaveRequest request);

    CustomerSaveResponse modelToCustomerSaveResponse(Customer customer);
}
