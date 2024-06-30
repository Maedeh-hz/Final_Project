package ir.maedehhz.final_project_spring.dto.order;

import ir.maedehhz.final_project_spring.model.custom_fields.Address;

import java.time.LocalDateTime;

public record OrderSaveRequest(long subserviceId, long customerId, String description,
                               Double suggestingPrice, LocalDateTime toDoDateAndTime,
                               Address address) {
}
