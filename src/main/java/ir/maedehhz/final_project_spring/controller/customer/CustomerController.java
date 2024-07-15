package ir.maedehhz.final_project_spring.controller.customer;

import cn.apiclub.captcha.Captcha;
import ir.maedehhz.final_project_spring.dto.PaymentSaveRequest;
import ir.maedehhz.final_project_spring.dto.comment.CommentSaveRequest;
import ir.maedehhz.final_project_spring.dto.comment.CommentSaveResponse;
import ir.maedehhz.final_project_spring.dto.customer.CustomerPasswordUpdateRequest;
import ir.maedehhz.final_project_spring.dto.customer.CustomerSaveRequest;
import ir.maedehhz.final_project_spring.dto.customer.CustomerSaveResponse;
import ir.maedehhz.final_project_spring.dto.order.OrderSaveRequest;
import ir.maedehhz.final_project_spring.dto.order.OrderSaveResponse;
import ir.maedehhz.final_project_spring.mapper.comment.CommentMapper;
import ir.maedehhz.final_project_spring.mapper.customer.CustomerMapper;
import ir.maedehhz.final_project_spring.mapper.order.OrderMapper;
import ir.maedehhz.final_project_spring.model.*;
import ir.maedehhz.final_project_spring.service.card.CardServiceImpl;
import ir.maedehhz.final_project_spring.service.comment.CommentServiceImpl;
import ir.maedehhz.final_project_spring.service.customer.CustomerServiceImpl;
import ir.maedehhz.final_project_spring.service.order.OrderServiceImpl;
import ir.maedehhz.final_project_spring.service.service.ServiceServiceImpl;
import ir.maedehhz.final_project_spring.service.suggestion.SuggestionServiceImpl;
import ir.maedehhz.final_project_spring.utility.CaptchaUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customer")
public class CustomerController {

    private final CustomerServiceImpl customerService;
    private final OrderServiceImpl orderService;
    private final ServiceServiceImpl serviceService;
    private final CommentServiceImpl commentService;
    private final SuggestionServiceImpl suggestionService;
    private final CardServiceImpl cardService;

    @PostMapping("/save-customer")
    public ResponseEntity<CustomerSaveResponse> saveCustomer(@RequestBody CustomerSaveRequest request){
        Customer customer = CustomerMapper.INSTANCE.customerSaveRequestToModel(request);

        Customer saved = customerService.save(customer);
        CustomerSaveResponse response = CustomerMapper.INSTANCE.modelToCustomerSaveResponse(saved);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/confirm-email")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<String> confirmEmail(@RequestParam(name = "token") String token){
        String confirmToken = customerService.confirmToken(token);
        return new ResponseEntity<>(confirmToken, HttpStatus.OK);
    }

    @PatchMapping("/update-customer-password")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<CustomerSaveResponse> updatePasswordForCustomer(@RequestBody CustomerPasswordUpdateRequest request){
        Customer updated = customerService.updatePassword(request.userId(), request.previousPass(), request.newPass(), request.newPass2());

        CustomerSaveResponse response = CustomerMapper.INSTANCE.modelToCustomerSaveResponse(updated);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/register-order")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<OrderSaveResponse> saveOrder(@RequestBody OrderSaveRequest request){
        Order order = OrderMapper.INSTANCE.orderSaveRequestToModel(request);

        Order saved = orderService.save(order, request.subserviceId(), request.customerId());
        OrderSaveResponse response = OrderMapper.INSTANCE.modelToOrderSaveResponse(saved);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("find-all-service")
    @PreAuthorize("hasRole('CUSTOMER')")
    @ResponseStatus(HttpStatus.FOUND)
    public List<Service> findAllService(){
        return serviceService.findAll();
    }

    @PostMapping("/save-comment")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<CommentSaveResponse> saveComment(@RequestBody CommentSaveRequest request){
        Comment comment = CommentMapper.INSTANCE.commentSaveRequestToModel(request);

        Comment saved = commentService.save(comment, request.orderId());
        CommentSaveResponse response = CommentMapper.INSTANCE.modelToCommentSaveResponse(saved);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/view-all-order-suggestions-by-expert-score")
    @PreAuthorize("hasRole('CUSTOMER')")
    @ResponseStatus(HttpStatus.FOUND)
    public List<Suggestion> findAllSuggestionByExpertScore(@RequestParam long orderId){
        return suggestionService.viewAllByExpertScore(orderId);
    }

    @GetMapping("/view-all-order-suggestions-by-price")
    @PreAuthorize("hasRole('CUSTOMER')")
    @ResponseStatus(HttpStatus.FOUND)
    public List<Suggestion> findAllSuggestionByPrice(@RequestParam long orderId){
        return suggestionService.viewAllByPrice(orderId);
    }

    @PatchMapping("/choosing-suggestion")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<OrderSaveResponse> choosingSuggestionForAnOrder(@RequestBody long suggestionId){
        Order updated = suggestionService.choosingExpert(suggestionId);

        OrderSaveResponse response = OrderMapper.INSTANCE.modelToOrderSaveResponse(updated);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/update-order-status-to-started")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<OrderSaveResponse> updatingOrderStatusToStarted(@RequestParam long orderId){
        Order updated = orderService.updateStatusToStarted(orderId);

        OrderSaveResponse response = OrderMapper.INSTANCE.modelToOrderSaveResponse(updated);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/update-order-status-to-done")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<OrderSaveResponse> updatingOrderStatusToDone(@RequestBody long orderId){
        Order updated = orderService.updateStatusToDone(orderId);

        OrderSaveResponse response = OrderMapper.INSTANCE.modelToOrderSaveResponse(updated);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping( "/payment")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Card> payment(@ModelAttribute PaymentSaveRequest request){
        Card card = Card.builder().cardNumber(request.cardNumber())
                .cvv2(request.cvv2())
                .expirationDate(request.expirationDate()).build();
        Card saved = cardService.save(card, request.orderId());

        return new ResponseEntity<>(saved, HttpStatus.OK);
    }

    private void getCaptcha(Card card){
        Captcha captcha = CaptchaUtil.createCaptcha(240, 70);
        card.setHiddenCaptcha(captcha.getAnswer());
        card.setCaptcha("");
        card.setRealCaptcha(CaptchaUtil.encodeCaptcha(captcha));
    }

}
