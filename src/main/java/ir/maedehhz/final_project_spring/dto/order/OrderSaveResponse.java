package ir.maedehhz.final_project_spring.dto.order;

import ir.maedehhz.final_project_spring.model.enums.OrderStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record OrderSaveResponse(Long subserviceId, Long customerId,
                                String description, Double suggestingPrice,
                                LocalDate registerDate, LocalDateTime toDoDateAndTime,
                                OrderStatus status, Long addressId) {
}
