package ir.maedehhz.final_project_spring.service.user_subservice;

import ir.maedehhz.final_project_spring.exception.DuplicateInfoException;
import ir.maedehhz.final_project_spring.exception.NotFoundException;
import ir.maedehhz.final_project_spring.exception.UnverifiedExpertException;
import ir.maedehhz.final_project_spring.model.Expert;
import ir.maedehhz.final_project_spring.model.Subservice;
import ir.maedehhz.final_project_spring.model.User_SubService;
import ir.maedehhz.final_project_spring.model.Wallet;
import ir.maedehhz.final_project_spring.model.enums.ExpertStatus;
import ir.maedehhz.final_project_spring.service.expert.ExpertServiceImpl;
import ir.maedehhz.final_project_spring.service.subservice.SubserviceServiceImpl;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserSubserviceServiceImplTest {

    @Autowired
    private User_SubserviceServiceImpl user_subserviceService;

    @Autowired
    private ExpertServiceImpl expertService;

    @Autowired
    private SubserviceServiceImpl subserviceService;

    private Expert expert2;

    @BeforeEach
    public void initialize() {
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
    public void addExpertToSubservice(){
        Expert byUsername = expertService.findByUsername("fatemeh@gmail.com");
        Subservice subserviceById = subserviceService.findById(1L);
        User_SubService userSubService = User_SubService.builder()
                .expert(byUsername)
                .subservice(subserviceById).build();

        User_SubService saved = user_subserviceService.save(userSubService);
        assertEquals(1, saved.getId());
    }

    @Test
    @Order(2)
    public void addExpertToSubserviceWithDuplicateInfo(){
        Expert byUsername = expertService.findByUsername("fatemeh@gmail.com");
        Subservice subserviceById = subserviceService.findById(1L);
        User_SubService userSubService = User_SubService.builder()
                .expert(byUsername)
                .subservice(subserviceById).build();

        DuplicateInfoException exception = assertThrows(DuplicateInfoException.class, () ->
                user_subserviceService.save(userSubService));

        assertEquals("Expert has been added to this Subservice before!", exception.getMessage());
    }

    @Test
    @Order(3)
    public void addExpertToSubserviceWithInvalidExpert(){
        expert2.setId(5L);
        expert2.setStatus(ExpertStatus.UNVERIFIED);
        Subservice subserviceById = subserviceService.findById(1L);
        User_SubService userSubService = User_SubService.builder()
                .expert(expert2)
                .subservice(subserviceById).build();

        UnverifiedExpertException exception = assertThrows(UnverifiedExpertException.class, ()->
                user_subserviceService.save(userSubService));

        assertEquals("Expert is not verified!", exception.getMessage());
    }

    @Test
    @Order(4)
    public void removeExpertFromSubservice(){
        boolean removed = user_subserviceService.remove(1L, 3L);
        assertTrue(removed);
    }

    @Test
    @Order(5)
    public void removeExpertFromSubserviceWithNoExpertUnderSubservice(){
        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                user_subserviceService.remove(1L, 3L));

        assertEquals("Expert with id 3 is not under service of this Subservice!", exception.getMessage());
    }
}
