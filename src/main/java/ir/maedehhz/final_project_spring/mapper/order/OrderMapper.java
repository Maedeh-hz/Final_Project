package ir.maedehhz.final_project_spring.mapper.order;

import ir.maedehhz.final_project_spring.dto.order.OrderFilteringResponse;
import ir.maedehhz.final_project_spring.dto.order.OrderFindAllResponse;
import ir.maedehhz.final_project_spring.dto.order.OrderSaveRequest;
import ir.maedehhz.final_project_spring.dto.order.OrderSaveResponse;
import ir.maedehhz.final_project_spring.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    Order orderSaveRequestToModel(OrderSaveRequest request);

    OrderSaveResponse modelToOrderSaveResponse(Order order);

    OrderFindAllResponse modelToOrderFindAllResponse(Order order);

    @Mapping(source = "address.province", target = "addressProvince")
    @Mapping(source = "address.id", target = "addressId")
    @Mapping(source = "expert.id", target = "expertId")
    @Mapping(source = "customer.id", target = "customerId")
    @Mapping(source = "service.id", target = "serviceId")
    @Mapping(source = "subservice.id", target = "subserviceId")
    OrderFilteringResponse modelToOrderFilteringResponse(Order order);
}
