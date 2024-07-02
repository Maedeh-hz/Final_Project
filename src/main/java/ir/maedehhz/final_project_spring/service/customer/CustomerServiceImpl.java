package ir.maedehhz.final_project_spring.service.customer;

import ir.maedehhz.final_project_spring.exception.*;
import ir.maedehhz.final_project_spring.model.Customer;
import ir.maedehhz.final_project_spring.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService{

    private final CustomerRepository repository;

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
        return repository.save(customer);
    }

    @Override
    public Customer findById(long customerId) {
        return repository.findById(customerId).orElseThrow(() ->
                new NotFoundException(String.format("Customer with id %s couldn't be found.", customerId)));
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
