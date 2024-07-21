package ir.maedehhz.final_project_spring.dto.order;

import java.time.LocalDateTime;

public record OrderSaveRequest(Long subserviceId, String description,
                               Double suggestingPrice, LocalDateTime toDoDateAndTime,
                               Long addressId) {
}
