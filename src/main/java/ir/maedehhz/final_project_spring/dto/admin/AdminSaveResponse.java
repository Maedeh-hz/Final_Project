package ir.maedehhz.final_project_spring.dto.admin;

import ir.maedehhz.final_project_spring.model.Wallet;

import java.time.LocalDate;

public record AdminSaveResponse(long id, String firstName, String lastName, String email, String username,
                                Wallet wallet, LocalDate registrationDate) {

}
