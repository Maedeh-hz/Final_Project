package ir.maedehhz.final_project_spring.dto.customer;

import ir.maedehhz.final_project_spring.model.Wallet;

public record CustomerSaveRequest(String firstName, String lastName,
                                  String email, String password,
                                  Wallet wallet) {
}
