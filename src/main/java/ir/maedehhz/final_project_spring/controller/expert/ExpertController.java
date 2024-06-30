package ir.maedehhz.final_project_spring.controller.expert;

import ir.maedehhz.final_project_spring.dto.expert.ExpertPasswordUpdateRequest;
import ir.maedehhz.final_project_spring.dto.expert.ExpertSaveRequest;
import ir.maedehhz.final_project_spring.dto.expert.ExpertSaveResponse;
import ir.maedehhz.final_project_spring.dto.suggestion.SuggestionSaveRequest;
import ir.maedehhz.final_project_spring.dto.suggestion.SuggestionSaveResponse;
import ir.maedehhz.final_project_spring.mapper.expert.ExpertMapper;
import ir.maedehhz.final_project_spring.mapper.suggestion.SuggestionMapper;
import ir.maedehhz.final_project_spring.model.Expert;
import ir.maedehhz.final_project_spring.model.Order;
import ir.maedehhz.final_project_spring.model.Suggestion;
import ir.maedehhz.final_project_spring.model.enums.ExpertStatus;
import ir.maedehhz.final_project_spring.service.comment.CommentServiceImpl;
import ir.maedehhz.final_project_spring.service.expert.ExpertServiceImpl;
import ir.maedehhz.final_project_spring.service.order.OrderServiceImpl;
import ir.maedehhz.final_project_spring.service.suggestion.SuggestionServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/expert")
public class ExpertController {

    private final ExpertServiceImpl expertService;
    private final SuggestionServiceImpl suggestionService;
    private final OrderServiceImpl orderService;
    private final CommentServiceImpl commentService;

    @PostMapping("/save-expert")
    public ResponseEntity<ExpertSaveResponse> saveExpert(@RequestBody ExpertSaveRequest request){
        Expert expert = ExpertMapper.INSTANCE.expertSaveRequestToModel(request);

        expert.setStatus(ExpertStatus.WAITING_FOR_VERIFYING);
        expert.setRegistrationDate(LocalDate.now());
        expert.setScore(0D);

        Expert saved = expertService.save(expert, request.imagePath());

        ExpertSaveResponse response = ExpertMapper.INSTANCE.modelToExpertSaveResponse(saved);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/update-expert-password")
    public ResponseEntity<ExpertSaveResponse> updatePasswordForExpert(@RequestBody ExpertPasswordUpdateRequest request){
        Expert updated = expertService.updatePassword(request.userId(), request.previousPass(), request.newPass(), request.newPass2());

        ExpertSaveResponse response = ExpertMapper.INSTANCE.modelToExpertSaveResponse(updated);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/register-suggestion")
    public ResponseEntity<SuggestionSaveResponse> saveSuggestion(@RequestBody SuggestionSaveRequest request){
        Suggestion suggestion = SuggestionMapper.INSTANCE.suggestionSaveRequestToModel(request);
        Suggestion saved = suggestionService.registerSuggestionForOrder(suggestion, request.expertId(), request.orderId());

        SuggestionSaveResponse response = SuggestionMapper.INSTANCE.modelToSuggestionSaveResponse(saved);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("view-all-orders-waiting-for-suggestion")
    public List<Order> findAllOrdersByStatus(){
        return orderService.findAllByStatusWaitingForSuggestion();
    }

    @GetMapping("/view-experts-score-by-order")
    public ResponseEntity<Double> viewExpertsScoreByOrder(@RequestBody long orderId){
        double v = commentService.viewExpertsScoreByOrder(orderId);

        return new ResponseEntity<>(v, HttpStatus.FOUND);
    }

    @GetMapping("/find-all-orders-by-experts-subservice")
    @ResponseStatus(HttpStatus.FOUND)
    public List<Order> findAllOrdersByExpertSubservice(@RequestBody long expertId){
        return orderService.findAllByExpertsSubservice(expertId);
    }


}
