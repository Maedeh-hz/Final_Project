package ir.maedehhz.final_project_spring.dto.order;

import ir.maedehhz.final_project_spring.dto.customer.CustomerSaveResponse;
import ir.maedehhz.final_project_spring.dto.expert.ExpertSaveResponse;
import ir.maedehhz.final_project_spring.model.enums.OrderStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record OrderSaveResponse(ExpertSaveResponse expert, CustomerSaveResponse customer,
                                String description, Double suggestingPrice,
                                LocalDate registerDate, LocalDateTime toDoDateAndTime,
                                OrderStatus status, Long addressId) {
}
