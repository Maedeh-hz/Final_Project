package ir.maedehhz.final_project_spring.token.service;

import ir.maedehhz.final_project_spring.token.ConfirmationToken;

public interface TokenService {
    ConfirmationToken save(ConfirmationToken token);
    ConfirmationToken findByToken(String token);

    int confirmedAt(String token);
}
