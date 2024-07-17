package ir.maedehhz.final_project_spring.controller.expert;

import ir.maedehhz.final_project_spring.dto.expert.ExpertPasswordUpdateRequest;
import ir.maedehhz.final_project_spring.dto.expert.ExpertSaveRequest;
import ir.maedehhz.final_project_spring.dto.expert.ExpertSaveResponse;
import ir.maedehhz.final_project_spring.dto.order.OrderFindAllResponse;
import ir.maedehhz.final_project_spring.dto.order.OrderSaveResponse;
import ir.maedehhz.final_project_spring.dto.suggestion.SuggestionFindAllResponse;
import ir.maedehhz.final_project_spring.dto.suggestion.SuggestionSaveRequest;
import ir.maedehhz.final_project_spring.dto.suggestion.SuggestionSaveResponse;
import ir.maedehhz.final_project_spring.mapper.expert.ExpertMapper;
import ir.maedehhz.final_project_spring.mapper.order.OrderMapper;
import ir.maedehhz.final_project_spring.mapper.suggestion.SuggestionMapper;
import ir.maedehhz.final_project_spring.model.Expert;
import ir.maedehhz.final_project_spring.model.Order;
import ir.maedehhz.final_project_spring.model.Subservice;
import ir.maedehhz.final_project_spring.model.Suggestion;
import ir.maedehhz.final_project_spring.service.comment.CommentServiceImpl;
import ir.maedehhz.final_project_spring.service.expert.ExpertServiceImpl;
import ir.maedehhz.final_project_spring.service.order.OrderServiceImpl;
import ir.maedehhz.final_project_spring.service.suggestion.SuggestionServiceImpl;
import ir.maedehhz.final_project_spring.service.user_subservice.User_SubserviceServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/expert")
public class ExpertController {

    private final ExpertServiceImpl expertService;
    private final SuggestionServiceImpl suggestionService;
    private final OrderServiceImpl orderService;
    private final CommentServiceImpl commentService;
    private final User_SubserviceServiceImpl userSubserviceService;

    @PostMapping("/save-expert")
    public ResponseEntity<ExpertSaveResponse> saveExpert(@RequestBody ExpertSaveRequest request){
        Expert expert = ExpertMapper.INSTANCE.expertSaveRequestToModel(request);

        Expert saved = expertService.save(expert, request.imagePath());

        ExpertSaveResponse response = ExpertMapper.INSTANCE.modelToExpertSaveResponse(saved);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/confirm-email")
    public ResponseEntity<String> confirmEmail(@RequestParam(name = "token") String token){
        String confirmToken = expertService.confirmToken(token);
        return new ResponseEntity<>(confirmToken, HttpStatus.OK);
    }

    @PatchMapping("/update-expert-password")
    @PreAuthorize("hasRole('EXPERT')")
    public ResponseEntity<ExpertSaveResponse> updatePasswordForExpert(@RequestBody ExpertPasswordUpdateRequest request){
        Expert updated = expertService.updatePassword(request.userId(), request.newPass(), request.newPass2());

        ExpertSaveResponse response = ExpertMapper.INSTANCE.modelToExpertSaveResponse(updated);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/register-suggestion")
    @PreAuthorize("hasRole('EXPERT')")
    public ResponseEntity<SuggestionSaveResponse> saveSuggestion(@RequestBody SuggestionSaveRequest request){
        Suggestion suggestion = SuggestionMapper.INSTANCE.suggestionSaveRequestToModel(request);
        Order order = orderService.findById(request.orderId());
        Expert expert = expertService.findById(request.expertId());
        Suggestion saved = suggestionService.registerSuggestionForOrder(suggestion, expert, order);

        SuggestionSaveResponse response = SuggestionMapper.INSTANCE.modelToSuggestionSaveResponse(saved);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/view-expert-score-by-order")
    @PreAuthorize("hasRole('EXPERT')")
    public ResponseEntity<Double> viewExpertsScoreByOrder(@RequestParam long orderId){
        double v = commentService.viewExpertsScoreByOrder(orderId);

        return new ResponseEntity<>(v, HttpStatus.FOUND);
    }

    @GetMapping("/find-all-subservices-of-expert")
    @ResponseStatus(HttpStatus.FOUND)
    @PreAuthorize("hasRole('EXPERT')")
    public List<Subservice> findAllSubservicesOfExpert(@RequestParam long expertId){
        return userSubserviceService.findAllByExpert_Id(expertId);
    }

    @GetMapping("/find-all-orders-by-subservice-waiting-for-suggestion")
    @PreAuthorize("hasRole('EXPERT')")
    @ResponseStatus(HttpStatus.FOUND)
    public List<OrderFindAllResponse> findAllOrdersByExpertSubservice(@RequestParam long subserviceId){
        return orderService.findAllBySubserviceWaitingForExpertSuggestion(subserviceId);
    }

    @GetMapping("/order-history")
    @PreAuthorize("hasRole('EXPERT')")
    public List<OrderSaveResponse> orderHistory(@RequestParam Long expertId){
        List<Order> all = orderService.findAllByExpert_Id(expertId);
        List<OrderSaveResponse> responses = new ArrayList<>();
        all.forEach(order -> responses.add(OrderMapper.INSTANCE.modelToOrderSaveResponse(order)));
        return responses;
    }

    @GetMapping("/suggestion-history")
    @PreAuthorize("hasRole('EXPERT')")
    public List<SuggestionFindAllResponse> findAllByExpertId(@RequestParam Long expertId){
        return suggestionService.findAllByExpertId(expertId);
    }

}
