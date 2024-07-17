package ir.maedehhz.final_project_spring.controller.admin;

import ir.maedehhz.final_project_spring.dto.admin.AdminSaveRequest;
import ir.maedehhz.final_project_spring.dto.admin.AdminSaveResponse;
import ir.maedehhz.final_project_spring.dto.expert.ExpertSaveResponse;
import ir.maedehhz.final_project_spring.dto.order.OrderFilteringResponse;
import ir.maedehhz.final_project_spring.dto.service.ServiceSaveRequest;
import ir.maedehhz.final_project_spring.dto.service.ServiceSaveResponse;
import ir.maedehhz.final_project_spring.dto.subservice.SubserviceBasePriceUpdateRequest;
import ir.maedehhz.final_project_spring.dto.subservice.SubserviceDescUpdateRequest;
import ir.maedehhz.final_project_spring.dto.subservice.SubserviceSaveRequest;
import ir.maedehhz.final_project_spring.dto.subservice.SubserviceSaveResponse;
import ir.maedehhz.final_project_spring.mapper.admin.AdminMapper;
import ir.maedehhz.final_project_spring.mapper.expert.ExpertMapper;
import ir.maedehhz.final_project_spring.mapper.service.ServiceMapper;
import ir.maedehhz.final_project_spring.mapper.subservice.SubserviceMapper;
import ir.maedehhz.final_project_spring.model.Admin;
import ir.maedehhz.final_project_spring.model.Expert;
import ir.maedehhz.final_project_spring.model.Service;
import ir.maedehhz.final_project_spring.model.Subservice;
import ir.maedehhz.final_project_spring.service.admin.AdminServiceImpl;
import ir.maedehhz.final_project_spring.service.expert.ExpertServiceImpl;
import ir.maedehhz.final_project_spring.service.order.OrderServiceImpl;
import ir.maedehhz.final_project_spring.service.service.ServiceServiceImpl;
import ir.maedehhz.final_project_spring.service.subservice.SubserviceServiceImpl;
import ir.maedehhz.final_project_spring.service.user.UserServiceImpl;
import ir.maedehhz.final_project_spring.service.user_subservice.User_SubserviceServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminController {
    private final UserServiceImpl userService;
    private final AdminServiceImpl adminService;
    private final ExpertServiceImpl expertService;
    private final ServiceServiceImpl serviceService;
    private final SubserviceServiceImpl subserviceService;
    private final OrderServiceImpl orderService;
    private final User_SubserviceServiceImpl userSubserviceService;

    @PostMapping("/save-admin")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AdminSaveResponse> saveAdmin(@RequestBody AdminSaveRequest request){
        Admin admin = AdminMapper.INSTANCE.adminSaveRequestToModel(request);
        Admin saved = adminService.save(admin);

        AdminSaveResponse response = AdminMapper.INSTANCE.modelToAdminSaveResponse(saved);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/save-service")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ServiceSaveResponse> saveService(@RequestBody ServiceSaveRequest request){
        Service service = ServiceMapper.INSTANCE.serviceSaveRequestToModel(request);

        Service saved = serviceService.save(service);
        ServiceSaveResponse response = ServiceMapper.INSTANCE.modelToServiceSaveResponse(saved);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/save-subservice")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SubserviceSaveResponse> saveSubservice(@RequestBody SubserviceSaveRequest request){
        Subservice subservice = SubserviceMapper.INSTANCE.subserviceSaveRequestToModel(request);

        Subservice saved = subserviceService.save(subservice, request.serviceId());
        SubserviceSaveResponse response = SubserviceMapper.INSTANCE.modelToSubserviceSaveResponse(saved);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/verifying-expert")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ExpertSaveResponse> verifyingExpert(@RequestParam long expertId){
        Expert updated = expertService.updateStatusToVerified(expertId);

        ExpertSaveResponse response = ExpertMapper.INSTANCE.modelToExpertSaveResponse(updated);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/updating-expert-status-unverified")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ExpertSaveResponse> unverifyingExpert(@RequestParam long expertId){
        Expert updated = expertService.updateStatusToUnverified(expertId);

        ExpertSaveResponse response = ExpertMapper.INSTANCE.modelToExpertSaveResponse(updated);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/add-expert-to-subservice")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> addExpertToSubservice(@RequestParam long expertId, long subserviceId){
        userSubserviceService.save(expertId, subserviceId);

        return new ResponseEntity<>("added successfully", HttpStatus.CREATED);
    }

    @PatchMapping("/remove-expert-from-subservice")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> removeExpertFromSubservice(@RequestParam long expertId, long subserviceId){
        userSubserviceService.remove(subserviceId, expertId);

        return new ResponseEntity<>("removed successfully", HttpStatus.OK);
    }

    @PatchMapping("/update-subservice-description")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SubserviceSaveResponse> updateSubserviceDescription(@RequestBody SubserviceDescUpdateRequest request){
        Subservice updated = subserviceService.updateDescription(request.id(), request.newDescription());

        SubserviceSaveResponse response = SubserviceMapper.INSTANCE.modelToSubserviceSaveResponse(updated);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/update-subservice-base-price")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<SubserviceSaveResponse> updateSubserviceBasePrice(@RequestBody SubserviceBasePriceUpdateRequest request){
        Subservice updated = subserviceService.updateBasePrice(request.id(), request.newBasePrice());

        SubserviceSaveResponse response = SubserviceMapper.INSTANCE.modelToSubserviceSaveResponse(updated);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/filtering-users")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.FOUND)
    public List filteringUsers(@RequestParam String dtype, String firstName,
                               String lastName, String email, Double score,
                               String expertise, String registerDate, String expertStatus){
        if (dtype.equals("Expert"))
            return expertService.filteringExperts(firstName, lastName, email, score, expertise, registerDate, expertStatus);

        return userService.filteringUsers(dtype, firstName, lastName, email, registerDate);
    }

    @GetMapping("/filtering-orders")
    @PreAuthorize("hasRole('ADMIN')")
    public List<OrderFilteringResponse> filteringOrders(@RequestParam
            Long userId, String registrationDate, String orderStatus,
            Long serviceId, Long subserviceId
    ){
        return orderService.filteringOrders(userId, registrationDate, orderStatus,
                serviceId, subserviceId);
    }
}
