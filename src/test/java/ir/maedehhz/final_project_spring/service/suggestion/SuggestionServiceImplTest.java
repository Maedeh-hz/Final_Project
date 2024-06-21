package ir.maedehhz.final_project_spring.service.suggestion;

import ir.maedehhz.final_project_spring.exception.NotYourBusinessException;
import ir.maedehhz.final_project_spring.exception.PriceUnderRangeException;
import ir.maedehhz.final_project_spring.model.Expert;
import ir.maedehhz.final_project_spring.model.Order;
import ir.maedehhz.final_project_spring.model.Suggestion;
import ir.maedehhz.final_project_spring.model.enums.OrderStatus;
import ir.maedehhz.final_project_spring.service.expert.ExpertServiceImpl;
import ir.maedehhz.final_project_spring.service.order.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SuggestionServiceImplTest {
    @Autowired
    private SuggestionServiceImpl suggestionService;

    @Autowired
    private ExpertServiceImpl expertService;

    @Autowired
    private OrderServiceImpl orderService;

    private Suggestion suggestion;

    @BeforeEach
    public void initialize(){
        suggestion = Suggestion.builder().description("test")
                .price(1100D)
                .registerDate(LocalDate.now())
                .workDuration(1.5).build();
    }

    @Test
    @org.junit.jupiter.api.Order(1)
    public void saveSuggestion(){
        Expert expertById = expertService.findById(3);
        Order orderById = orderService.findById(1);
        suggestion.setExpert(expertById);
        suggestion.setOrder(orderById);

        Suggestion saved = suggestionService.save(suggestion);
        assertEquals(1, saved.getId());
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    public void saveSuggestionWithNotYouBusinessExc(){
        Expert expertById = expertService.findById(4);
        Order orderById = orderService.findById(2);
        suggestion.setExpert(expertById);
        suggestion.setOrder(orderById);

        NotYourBusinessException exception = assertThrows(NotYourBusinessException.class, () ->
                suggestionService.save(suggestion));

        assertEquals("You are not under service of this orders Subservice.", exception.getMessage());
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    public void saveSuggestionWithNotYouBusinessExcOrderStatus(){
        Expert expertById = expertService.findById(3);
        Order orderById = orderService.findById(2);
        orderById.setStatus(OrderStatus.WAITING_FOR_EXPERT_TO_TAKE);
        suggestion.setExpert(expertById);
        suggestion.setOrder(orderById);

        NotYourBusinessException exception = assertThrows(NotYourBusinessException.class, () ->
                suggestionService.save(suggestion));

        assertEquals("Order is not waiting for any suggestions!", exception.getMessage());
    }

    @Test
    @org.junit.jupiter.api.Order(4)
    public void saveSuggestionWithPriceUnderRange(){
        Expert expertById = expertService.findById(3);
        Order orderById = orderService.findById(1);
        suggestion.setPrice(400D);
        suggestion.setExpert(expertById);
        suggestion.setOrder(orderById);

        PriceUnderRangeException exception = assertThrows(PriceUnderRangeException.class, () ->
                suggestionService.save(suggestion));

        assertEquals("Suggesting price from expert is less than Subservice base price!", exception.getMessage());
    }

}
