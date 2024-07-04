package ir.maedehhz.final_project_spring.dto;

public record PaymentSaveRequest(Long orderId, String cardNumber, String cvv2, String expirationDate) {
}
