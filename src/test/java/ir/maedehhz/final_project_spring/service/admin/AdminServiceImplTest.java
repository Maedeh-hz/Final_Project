package ir.maedehhz.final_project_spring.service.admin;

import ir.maedehhz.final_project_spring.exception.DuplicateInfoException;
import ir.maedehhz.final_project_spring.model.Admin;
import ir.maedehhz.final_project_spring.model.Wallet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class AdminServiceImplTest {
    @Autowired
    private AdminServiceImpl adminService;

    private Admin admin;

    @BeforeEach
    public void initialize(){
        admin = Admin.builder()
                .firstName("maede")
                .lastName("hosseinzade")
                .email("maede@gmail.com")
                .username("maede@gmail.com")
                .password("Maede@123")
                .registrationDate(LocalDate.now())
                .wallet(new Wallet()).build();
    }

    @Test
    public void saveAdmin() {
        Admin saved = adminService.save(admin);
        assertEquals(1, saved.getId());
    }


    @Test
    public void saveAdminWithDuplicateInfo(){
        DuplicateInfoException exception = assertThrows(DuplicateInfoException.class, () ->
                adminService.save(admin));
        assertEquals("User with username maede@gmail.com exists!", exception.getMessage());
    }
}
