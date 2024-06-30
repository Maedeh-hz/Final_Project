package ir.maedehhz.final_project_spring.dto.order;

import ir.maedehhz.final_project_spring.model.Customer;
import ir.maedehhz.final_project_spring.model.Subservice;
import ir.maedehhz.final_project_spring.model.custom_fields.Address;
import ir.maedehhz.final_project_spring.model.enums.OrderStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record OrderSaveResponse(Subservice subservice, Customer customer,
                                String description, Double suggestingPrice,
                                LocalDate registerDate, LocalDateTime toDoDateAndTime,
                                OrderStatus status, Address address) {
}
