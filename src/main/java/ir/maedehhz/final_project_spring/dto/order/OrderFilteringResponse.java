package ir.maedehhz.final_project_spring.dto.order;

import ir.maedehhz.final_project_spring.model.enums.OrderStatus;

import java.time.LocalDateTime;

public record OrderFilteringResponse(Long id, LocalDateTime registerDate, Long subserviceId, Long serviceId,
                                     Long customerId, String description, Double suggestingPrice,
                                     LocalDateTime toDoDateAndTime, OrderStatus status,
                                     Long expertId, Long addressId,
                                     String addressProvince) {
}
