package ir.maedehhz.final_project_spring.service.expert;

import ir.maedehhz.final_project_spring.dto.expert.ExpertFilteringResponse;
import ir.maedehhz.final_project_spring.email.EmailService;
import ir.maedehhz.final_project_spring.exception.*;
import ir.maedehhz.final_project_spring.mapper.expert.ExpertMapper;
import ir.maedehhz.final_project_spring.model.Expert;
import ir.maedehhz.final_project_spring.model.enums.ExpertStatus;
import ir.maedehhz.final_project_spring.model.enums.Role;
import ir.maedehhz.final_project_spring.repository.ExpertRepository;
import ir.maedehhz.final_project_spring.service.wallet.WalletServiceImpl;
import ir.maedehhz.final_project_spring.token.ConfirmationToken;
import ir.maedehhz.final_project_spring.token.service.TokenServiceImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class ExpertServiceImpl implements ExpertService{

    private final ExpertRepository repository;
    private final EntityManager entityManager;
    private final BCryptPasswordEncoder passwordEncoder;
    private final TokenServiceImpl tokenService;
    private final EmailService emailService;
    private final WalletServiceImpl walletService;

    @Override
    public Expert save(Expert expert, String imagePath) {
        if (repository.existsByUsername(expert.getEmail()))
            throw new DuplicateInfoException
                    (String.format("User with username %s exists!", expert.getEmail()));

        if (!validatePass(expert.getPassword()))
            throw new InvalidInputException("Users entrance password is invalid!");

        if (!validateEmail(expert.getEmail()))
            throw new InvalidInputException("Users entrance email is invalid!");

        if (!imagePath.endsWith(".png"))
            throw new ImageFormatException("The image format should be in PNG!");

        expert.setImage(getBytesForExpert(imagePath));

        if (expert.getImage().length > 300 * 1024)
            throw new ImageLengthOutOfBoundException("The uploaded image size is more than 300KB!");

        expert.setUsername(expert.getEmail());
        expert.setStatus(ExpertStatus.WAITING_FOR_VERIFYING);
        expert.setScore(0D);
        expert.setRegistrationDate(LocalDateTime.now());
        expert.setRole(Role.ROLE_EXPERT);
        expert.setPassword(passwordEncoder.encode(expert.getPassword()));
        Expert saved = repository.save(expert);

        walletService.register(saved);
        
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15),
                saved
        );
        tokenService.save(confirmationToken);
        String link = "http://localhost:8080/api/v1/customer/confirm-email?token=" + token;
        emailService.send(
                saved.getEmail(),
                EmailService.buildEmail(saved.getFirstName(), link)
        );
        return saved;
    }

    @Override
    public Expert updateScore(Expert expert) {
        return repository.save(expert);
    }

    @Override
    public Expert findByUsername(String username) {
        if (!repository.existsByUsername(username))
            throw new NotFoundException(String.format("User with username %s not found!", username));
        return repository.findByUsername(username);
    }

    @Override
    public void enableExpert(String username) {
        Expert expert = findByUsername(username);
        expert.setEnabled(true);
        repository.save(expert);
    }

    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = tokenService.findByToken(token);

        if (confirmationToken.getConfirmedAt() != null)
            throw new InvalidRequestException("email already confirmed!");


        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now()))
            throw new InvalidRequestException("token expired!");


        tokenService.confirmedAt(token);
        enableExpert(confirmationToken.getUser().getEmail());
        return "confirmed";
    }

    @Override
    public Expert updatePassword(long expertId, String previousPass, String newPass, String newPass2) {
        Expert expert = findById(expertId);
        if (!previousPass.equals(expert.getPassword()))
            throw new PasswordMismatchException("Wrong password!");
        if (!newPass.equals(newPass2))
            throw new PasswordMismatchException("The first and second passwords are not the same!");
        if (!validatePass(newPass))
            throw new InvalidInputException("Invalid password!");
        if (validatePass(newPass))
            expert.setPassword(newPass);
        return repository.save(expert);
    }

    @Override
    public Expert updateStatusToVerified(long expertId) {
        Expert byId = findById(expertId);
        byId.setStatus(ExpertStatus.VERIFIED);
        return repository.save(byId);
    }

    @Override
    public Expert updateStatusToUnverified(long expertId) {
        Expert byId = findById(expertId);
        byId.setStatus(ExpertStatus.UNVERIFIED);
        return repository.save(byId);
    }

    @Override
    public Expert findById(long id) {
        return repository.findById(id).orElseThrow(() ->
                new NotFoundException(String.format("Expert with id %s couldn't be found.", id)));
    }

    @Override
    public List<ExpertFilteringResponse> filteringExperts(String firstName, String lastName,
                                                          String email, Double score, String expertise,
                                                          String registrationDate, String expertStatus) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Expert> cq = cb.createQuery(Expert.class);

        Root<Expert> root = cq.from(Expert.class);
        List<Predicate> predicates = new ArrayList<>();

        if (score != null)
            predicates.add(cb.equal(root.get("score"), score));

        if (firstName != null)
            predicates.add(cb.like(root.get("firstName"), "%" + firstName + "%"));

        if (lastName != null)
            predicates.add(cb.like(root.get("lastName"), "%" + lastName + "%"));

        if (email != null)
            predicates.add(cb.like(root.get("email"), "%" + email + "%"));

        if (expertise != null)
            predicates.add(cb.like(root.get("expertise"), "%" + expertise + "%"));

        if (registrationDate != null)
            predicates.add(cb.like(root.get("registrationDate"), "%" + registrationDate + "%"));

        if (expertStatus != null )
            predicates.add(cb.equal(root.get("status"), expertStatus.toUpperCase(Locale.ROOT)));

        cq.where(predicates.toArray(new Predicate[0]));
        List<Expert> resultList = entityManager.createQuery(cq).getResultList();
        List<ExpertFilteringResponse> responses = new ArrayList<>();
        resultList.forEach(expert ->
                responses.add(ExpertMapper.INSTANCE.modelToExpertFilteringResponse(expert))
        );
        return responses;
    }

    private boolean validatePass(String pass){
        Pattern validPass = Pattern.compile("^.*(?=.{8,})(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).*$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = validPass.matcher(pass);
        return matcher.matches();
    }

    private boolean validateEmail(String email) {
        Pattern VALID_EMAIL_ADDRESS_REGEX =
                Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.matches();
    }

    public byte[] getBytesForExpert(String path) {
        byte[] array;
        try {
            array = Files.readAllBytes(Paths.get(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return array;
    }


}
