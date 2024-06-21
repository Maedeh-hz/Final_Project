package ir.maedehhz.final_project_spring.service.customer;

import ir.maedehhz.final_project_spring.exception.DuplicateInfoException;
import ir.maedehhz.final_project_spring.exception.InvalidEmailException;
import ir.maedehhz.final_project_spring.exception.InvalidPasswordException;
import ir.maedehhz.final_project_spring.exception.PasswordMismatchException;
import ir.maedehhz.final_project_spring.model.Customer;
import ir.maedehhz.final_project_spring.model.Wallet;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CustomerServiceImplTest {

    @Autowired
    private CustomerServiceImpl customerService;

    private Customer customer;
    private Customer customer2;

    @BeforeEach
    public void initialize(){
        customer = Customer.builder()
                .firstName("radvin")
                .lastName("mohammadi")
                .email("radirad@gmail.com")
                .username("radirad@gmail.com")
                .password("Radirad@123")
                .registrationDate(LocalDate.now())
                .wallet(new Wallet()).build();

        customer2 = Customer.builder()
                .firstName("roham")
                .lastName("eftekhari")
                .email("roham")
                .username("roham")
                .password("RaOham@123")
                .registrationDate(LocalDate.now())
                .wallet(new Wallet()).build();
    }

    @Test
    @Order(1)
    public void saveCustomer(){
        Customer saved = customerService.save(customer);
        assertEquals(2, saved.getId());
    }


    @Test
    @Order(2)
    public void saveCustomerWithDuplicateInfo(){
        DuplicateInfoException exception = assertThrows(DuplicateInfoException.class, () ->
                customerService.save(customer));
        assertEquals("User with username radirad@gmail.com exists!", exception.getMessage());
    }

    @Test
    @Order(3)
    public void saveCustomerWithInvalidEmail(){
        InvalidEmailException exception = assertThrows(InvalidEmailException.class, ()->
                customerService.save(customer2));
        assertEquals("Users entrance email is invalid!", exception.getMessage());
    }


    @Test
    @Order(4)
    public void updatePasswordForCustomer(){
        Customer founded = customerService.findByUsername(customer.getUsername());
        Customer updated = customerService.updatePassword(
                founded, "Radvin@123", "Radvin@123"
        );
        assertNotEquals("Radirad@123", updated.getPassword());
    }


    @Test
    @Order(5)
    public void updatePasswordForCustomerInvalidVer(){
        Customer founded = customerService.findByUsername(customer.getUsername());
        InvalidPasswordException exception = assertThrows(InvalidPasswordException.class, () ->
                customerService.updatePassword(founded, "radirad", "radirad"));
        assertEquals("Invalid password!", exception.getMessage());
    }

    @Test
    @Order(6)
    public void updatePasswordForCustomerPasswordMismatchVer(){
        Customer founded = customerService.findByUsername(customer.getUsername());
        PasswordMismatchException exception = assertThrows(PasswordMismatchException.class, () ->
                customerService.updatePassword(founded, "radi", "radirad"));
        assertEquals("The first and second passwords are not the same!", exception.getMessage());
    }

}
