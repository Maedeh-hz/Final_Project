package ir.maedehhz.final_project_spring.dto.customer;

import ir.maedehhz.final_project_spring.model.Wallet;

import java.time.LocalDate;

public record CustomerSaveResponse(long id, String firstName, String lastName,
                                   String email, String username,
                                   Wallet wallet, LocalDate registrationDate) {
}
