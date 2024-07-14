package ir.maedehhz.final_project_spring.token.service;

import ir.maedehhz.final_project_spring.exception.NotFoundException;
import ir.maedehhz.final_project_spring.token.TokenRepository;
import ir.maedehhz.final_project_spring.token.ConfirmationToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService{

    private final TokenRepository repository;


    @Override
    public ConfirmationToken save(ConfirmationToken token) {
        return repository.save(token);
    }

    @Override
    public ConfirmationToken findByToken(String token) {
        return repository.findByToken(token).orElseThrow(() ->
                new NotFoundException("Token not found!"));
    }

    @Override
    public int confirmedAt(String token) {
        return repository.updateConfirmedAt(token, LocalDateTime.now());
    }
}
