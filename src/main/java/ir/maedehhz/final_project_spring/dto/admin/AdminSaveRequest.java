package ir.maedehhz.final_project_spring.dto.admin;

import ir.maedehhz.final_project_spring.model.Wallet;

public record AdminSaveRequest(String firstName, String lastName,
                               String email, String username, String password){
}
