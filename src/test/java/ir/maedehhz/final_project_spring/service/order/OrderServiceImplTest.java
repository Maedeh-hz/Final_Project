package ir.maedehhz.final_project_spring.service.order;

import ir.maedehhz.final_project_spring.exception.InvalidDateForOrderException;
import ir.maedehhz.final_project_spring.model.Customer;
import ir.maedehhz.final_project_spring.model.Order;
import ir.maedehhz.final_project_spring.model.Subservice;
import ir.maedehhz.final_project_spring.model.custom_fields.Address;
import ir.maedehhz.final_project_spring.model.enums.OrderStatus;
import ir.maedehhz.final_project_spring.service.customer.CustomerServiceImpl;
import ir.maedehhz.final_project_spring.service.subservice.SubserviceServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OrderServiceImplTest {
    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    private CustomerServiceImpl customerService;

    @Autowired
    private SubserviceServiceImpl subserviceService;

    private Order order;

    @BeforeEach
    public void initialize() {
        order = Order.builder().address(new Address("tehran", "tehran", "mmm", 40))
                .registerDate(LocalDate.now())
                .status(OrderStatus.WAITING_FOR_EXPERT_SUGGESTION)
                .description("test")
                .suggestingPrice(1100D)
                .toDoDateAndTime(LocalDateTime.of(2024, 6, 25, 12, 30))
                .build();
    }

    @Test
    @org.junit.jupiter.api.Order(1)
    public void saveOrder(){
        Customer byUsername = customerService.findByUsername("radirad@gmail.com");
        Subservice byId = subserviceService.findById(1L);
        order.setCustomer(byUsername);
        order.setSubservice(byId);

        Order order1 = Order.builder()
                .address(new Address("nnn", "nnn", "nnn", 10))
                .status(OrderStatus.WAITING_FOR_EXPERT_SUGGESTION)
                .registerDate(LocalDate.now())
                .description("test")
                .customer(byUsername)
                .suggestingPrice(7000D)
                .toDoDateAndTime(LocalDateTime.of(2024,6,25,12,30))
                .subservice(byId).build();

        Order saved = orderService.save(order);
        orderService.save(order1);
        assertEquals(1, saved.getId());
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    public void saveOrderWithWrongDate(){
        Customer byUsername = customerService.findByUsername("radirad@gmail.com");
        Subservice byId = subserviceService.findById(1L);
        order.setCustomer(byUsername);
        order.setSubservice(byId);
        order.setToDoDateAndTime(LocalDateTime.of(2024, 6, 10, 12, 30));

        InvalidDateForOrderException exception = assertThrows(InvalidDateForOrderException.class, () ->
                orderService.save(order));

        assertEquals("The entered date is before today!", exception.getMessage());
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    public void updateOrderStatusToWaitingForExpertToTake(){
        Order updated = orderService.updateStatusToWaitingForExpertToTake(2);
        assertEquals(OrderStatus.WAITING_FOR_EXPERT_TO_TAKE, updated.getStatus());
    }

    @Test
    @org.junit.jupiter.api.Order(4)
    public void updateOrderStatusToWaitingForExpertToVisit(){
        Order updated = orderService.updateStatusToWaitingForExpertToVisit(2);
        assertEquals(OrderStatus.WAITING_FOR_EXPERT_TO_VISIT, updated.getStatus());
    }

    @Test
    @org.junit.jupiter.api.Order(5)
    public void updateOrderStatusToStarted(){
        Order updated = orderService.updateStatusToStarted(2);
        assertEquals(OrderStatus.STARTED, updated.getStatus());
    }

    @Test
    @org.junit.jupiter.api.Order(6)
    public void updateOrderStatusToDone(){
        Order updated = orderService.updateStatusToDone(2);
        assertEquals(OrderStatus.DONE, updated.getStatus());
    }
}
