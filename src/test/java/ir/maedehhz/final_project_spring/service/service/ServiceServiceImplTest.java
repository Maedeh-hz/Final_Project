package ir.maedehhz.final_project_spring.service.service;

import ir.maedehhz.final_project_spring.exception.DuplicateInfoException;
import ir.maedehhz.final_project_spring.model.Admin;
import ir.maedehhz.final_project_spring.model.Service;
import ir.maedehhz.final_project_spring.service.admin.AdminServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class ServiceServiceImplTest {

    @Autowired
    private ServiceServiceImpl serviceService;

    @Autowired
    private AdminServiceImpl adminService;

    private Service service;

    @Test
    public void saveService(){
        Admin adminServiceById = adminService.findById(1L);
        service = Service.builder()
                .serviceName("test1")
                .registeredAdmin(adminServiceById).build();
        Service saved = serviceService.save(service);
        assertEquals(1, saved.getId());
    }

    @Test
    public void saveServiceWithDuplicateInfo(){
        service = Service.builder()
                .serviceName("test1")
                .build();
        DuplicateInfoException exception = assertThrows(DuplicateInfoException.class, ()->
                serviceService.save(service));
        assertEquals("Service with name test1 exists!", exception.getMessage());
    }

    @Test
    public void findAll(){
        List<Service> all = serviceService.findAll();
        assertEquals(1, all.get(0).getId());
    }
}
