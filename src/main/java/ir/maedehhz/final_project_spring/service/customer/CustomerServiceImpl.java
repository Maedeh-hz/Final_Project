package ir.maedehhz.final_project_spring.service.customer;

import ir.maedehhz.final_project_spring.email.EmailService;
import ir.maedehhz.final_project_spring.exception.DuplicateInfoException;
import ir.maedehhz.final_project_spring.exception.InvalidInputException;
import ir.maedehhz.final_project_spring.exception.NotFoundException;
import ir.maedehhz.final_project_spring.exception.PasswordMismatchException;
import ir.maedehhz.final_project_spring.model.Customer;
import ir.maedehhz.final_project_spring.model.enums.Role;
import ir.maedehhz.final_project_spring.repository.CustomerRepository;
import ir.maedehhz.final_project_spring.token.ConfirmationToken;
import ir.maedehhz.final_project_spring.token.service.TokenServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService{

    private final CustomerRepository repository;
    private final TokenServiceImpl tokenService;
//    private final BCryptPasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Override
    public Customer save(Customer customer) {
        if (repository.existsByUsername(customer.getEmail()))
            throw new DuplicateInfoException
                    (String.format("User with username %s exists!", customer.getEmail()));
        if (!validatePass(customer.getPassword()))
            throw new InvalidInputException("Users entrance password is invalid!");
        if (!validateEmail(customer.getEmail()))
            throw new InvalidInputException("Users entrance email is invalid!");

        customer.setUsername(customer.getEmail());
        customer.setRegistrationDate(LocalDate.now());
        customer.setRole(Role.ROLE_CUSTOMER);
//        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        Customer saved = repository.save(customer);

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

    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = tokenService.findByToken(token);

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        tokenService.confirmedAt(token);
        enableCustomer(confirmationToken.getUser().getEmail());
        return "confirmed";
    }

    @Override
    public Customer findById(long customerId) {
        return repository.findById(customerId).orElseThrow(() ->
                new NotFoundException(String.format("Customer with id %s couldn't be found.", customerId)));
    }

    @Override
    public Customer findByUserName(String username) {
        return repository.findByUsername(username).orElseThrow(() ->
                new NotFoundException(String.format(
                        "No user with username %s founded!", username
                )));
    }

    @Override
    public void enableCustomer(String username) {
        Customer customer = findByUserName(username);
        customer.setEnabled(true);
        repository.save(customer);
    }

    @Override
    public Customer updatePassword(long customerId, String previousPass, String newPass, String newPass2) {
        Customer customer = findById(customerId);
        if (!newPass.equals(newPass2))
            throw new PasswordMismatchException("The first and second passwords are not the same!");
        if (!validatePass(newPass))
            throw new InvalidInputException("Invalid password!");
        if (validatePass(newPass))
            customer.setPassword(newPass);
        return repository.save(customer);
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
}
