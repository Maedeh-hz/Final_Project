package ir.maedehhz.final_project_spring.service.expert;

import ir.maedehhz.final_project_spring.exception.*;
import ir.maedehhz.final_project_spring.model.Expert;
import ir.maedehhz.final_project_spring.model.Wallet;
import ir.maedehhz.final_project_spring.model.enums.ExpertStatus;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ExpertServiceImplTest {

    @Autowired
    private ExpertServiceImpl expertService;
    private Expert expert;
    private final String imagePathJPG = "C:\\Users\\RenderKar\\OneDrive\\Pictures\\Screenshots\\photo_2024-06-18_13-17-51.jpg";
    private Expert expert2;
    private final String imagePathPNG2MG = "C:\\Users\\RenderKar\\OneDrive\\Pictures\\Screenshots\\Screenshot 2023-12-31 124017.png";

    @BeforeEach
    public void initialize() {
        expert = Expert.builder()
                .firstName("fatemeh")
                .lastName("eftekari")
                .email("fatemeh@gmail.com")
                .username("fatemeh@gmail.com")
                .password("Fatemeh@123")
                .registrationDate(LocalDate.now())
                .status(ExpertStatus.WAITING_FOR_VERIFYING)
                .wallet(new Wallet())
                .build();

        expert2 = Expert.builder()
                .firstName("reza")
                .lastName("eftekari")
                .email("reza@gmail.com")
                .username("reza@gmail.com")
                .password("Reza@123")
                .registrationDate(LocalDate.now())
                .status(ExpertStatus.WAITING_FOR_VERIFYING)
                .wallet(new Wallet())
                .build();
    }

    @Test
    @Order(1)
    public void saveExpert(){
        String imagePathPNGAnd200KB = "C:\\Users\\RenderKar\\OneDrive\\Desktop\\Screenshot 2024-01-03 224225.png";
        Expert saved = expertService.save(expert, imagePathPNGAnd200KB);
        assertEquals(3, saved.getId());
    }


    @Test
    @Order(2)
    public void saveExpertWithDuplicateInfo(){
        DuplicateInfoException exception = assertThrows(DuplicateInfoException.class, () ->
                expertService.save(expert, imagePathJPG));
        assertEquals("User with username fatemeh@gmail.com exists!", exception.getMessage());
    }


    @Test
    @Order(3)
    public void saveExpertWithInvalidEmail(){
        expert2.setEmail("reza");
        InvalidEmailException exception = assertThrows(InvalidEmailException.class, ()->
                expertService.save(expert2, imagePathJPG));
        assertEquals("Users entrance email is invalid!", exception.getMessage());
    }

    @Test
    @Order(4)
    public void saveExpertWithWrongFormatImage(){
        ImageFormatException exception = assertThrows(ImageFormatException.class, () ->
                expertService.save(expert2, imagePathJPG));
        assertEquals("The image format should be in PNG!", exception.getMessage());
    }

    @Test
    @Order(5)
    public void saveExpertWithHighVolumeImage(){
        ImageLengthOutOfBoundException exception = assertThrows(ImageLengthOutOfBoundException.class, () ->
                expertService.save(expert2, imagePathPNG2MG));
        assertEquals("The uploaded image size is more than 300KB!", exception.getMessage());
    }


    @Test
    @Order(6)
    public void updatePasswordForExpert(){
        Expert founded = expertService.findByUsername(expert.getUsername());
        Expert updated = expertService.updatePassword(founded, "FAti@123", "FAti@123");
        assertNotEquals("Fatemeh@123", updated.getPassword());
    }

    @Test
    @Order(7)
    public void updatePasswordForExpertInvalidVer(){
        Expert founded = expertService.findByUsername(expert.getUsername());
        InvalidPasswordException exception = assertThrows(InvalidPasswordException.class, () ->
                expertService.updatePassword(founded, "fat", "fat"));
        assertEquals("Invalid password!", exception.getMessage());
    }

    @Test
    @Order(8)
    public void updatePasswordForExpertPasswordMismatchVer(){
        Expert founded = expertService.findByUsername(expert.getUsername());
        PasswordMismatchException exception = assertThrows(PasswordMismatchException.class, () ->
                expertService.updatePassword(founded, "fati", "fatif"));
        assertEquals("The first and second passwords are not the same!", exception.getMessage());
    }

    @Test
    @Order(9)
    public void updateExpertStatusToVerified(){
        Expert updated = expertService.updateStatusToVerified(3);
        assertEquals("VERIFIED", String.valueOf(updated.getStatus()));
    }

    @Test
    @Order(10)
    public void updateExpertStatusToUnverified(){
        Expert updated = expertService.updateStatusToUnverified(3);
        assertEquals("UNVERIFIED", String.valueOf(updated.getStatus()));
    }

}
