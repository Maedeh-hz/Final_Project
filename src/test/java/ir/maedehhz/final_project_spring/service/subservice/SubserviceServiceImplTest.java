package ir.maedehhz.final_project_spring.service.subservice;

import ir.maedehhz.final_project_spring.exception.DuplicateInfoException;
import ir.maedehhz.final_project_spring.exception.NotFoundException;
import ir.maedehhz.final_project_spring.exception.SameInfoException;
import ir.maedehhz.final_project_spring.model.Service;
import ir.maedehhz.final_project_spring.model.Subservice;
import ir.maedehhz.final_project_spring.service.service.ServiceServiceImpl;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SubserviceServiceImplTest {

    @Autowired
    private SubserviceServiceImpl subserviceService;

    @Autowired
    private ServiceServiceImpl serviceService;

    private Subservice subservice;

    @Test
    @Order(1)
    public void saveSubservice(){
        Service serviceById = serviceService.findById(1);
        subservice = Subservice.builder()
                .name("test1")
                .service(serviceById)
                .basePrice(900D)
                .description("for test")
                .build();
        Subservice saved = subserviceService.save(subservice);
        assertEquals(1, saved.getId());
    }

    @Test
    @Order(2)
    public void saveSubserviceWithDuplicateInfo(){
        Service serviceById = serviceService.findById(1);
        subservice = Subservice.builder()
                .name("test1")
                .service(serviceById)
                .basePrice(900D)
                .description("for test")
                .build();
        DuplicateInfoException exception = assertThrows(DuplicateInfoException.class, () ->
                subserviceService.save(subservice));
        assertEquals("Subservice with name test1 exists!", exception.getMessage());
    }

    @Test
    @Order(3)
    public void saveSubserviceWithNoSuchServiceExc(){
        Service service1 = Service.builder().serviceName("aha").build();
        subservice = Subservice.builder()
                .name("test2")
                .service(service1)
                .basePrice(900D)
                .description("for test")
                .build();
        NotFoundException exception = assertThrows(NotFoundException.class, () ->
                subserviceService.save(subservice));
        assertEquals("Service with name aha doesn't exist.", exception.getMessage());
    }

    @Test
    @Order(4)
    public void updateDescriptionOfSubservice(){
        Subservice updated = subserviceService.updateDescription(1L, "for test 1");
        assertEquals("for test 1", updated.getDescription());
    }

    @Test
    @Order(5)
    public void updateDescriptionOfSubserviceWithSameInfo(){
        SameInfoException exception = assertThrows(SameInfoException.class, () ->
                subserviceService.updateDescription(1L, "for test 1"));
        assertEquals("The description of subservice is already this!", exception.getMessage());
    }

    @Test
    @Order(6)
    public void updateBasePriceOfSubservice(){
        Subservice updated = subserviceService.updateBasePrice(1L, 1000);
        assertEquals(1000, updated.getBasePrice());
    }

    @Test
    @Order(7)
    public void updateBasePriceOfSubserviceWithSameInfo(){
        SameInfoException exception = assertThrows(SameInfoException.class, () ->
                subserviceService.updateBasePrice(1L, 1000));
        assertEquals("The base price of subservice is already this!", exception.getMessage());
    }
}
