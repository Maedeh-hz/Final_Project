package ir.maedehhz.final_project_spring.controller.admin;

import ir.maedehhz.final_project_spring.dto.admin.AdminSaveRequest;
import ir.maedehhz.final_project_spring.dto.admin.AdminSaveResponse;
import ir.maedehhz.final_project_spring.dto.expert.ExpertSaveResponse;
import ir.maedehhz.final_project_spring.dto.service.ServiceSaveRequest;
import ir.maedehhz.final_project_spring.dto.service.ServiceSaveResponse;
import ir.maedehhz.final_project_spring.dto.subservice.SubserviceBasePriceUpdateRequest;
import ir.maedehhz.final_project_spring.dto.subservice.SubserviceDescUpdateRequest;
import ir.maedehhz.final_project_spring.dto.subservice.SubserviceSaveRequest;
import ir.maedehhz.final_project_spring.dto.subservice.SubserviceSaveResponse;
import ir.maedehhz.final_project_spring.dto.user_sub.ExpertToSubserviceRequest;
import ir.maedehhz.final_project_spring.mapper.admin.AdminMapper;
import ir.maedehhz.final_project_spring.mapper.expert.ExpertMapper;
import ir.maedehhz.final_project_spring.mapper.service.ServiceMapper;
import ir.maedehhz.final_project_spring.mapper.subservice.SubserviceMapper;
import ir.maedehhz.final_project_spring.model.*;
import ir.maedehhz.final_project_spring.service.admin.AdminServiceImpl;
import ir.maedehhz.final_project_spring.service.expert.ExpertServiceImpl;
import ir.maedehhz.final_project_spring.service.service.ServiceServiceImpl;
import ir.maedehhz.final_project_spring.service.subservice.SubserviceServiceImpl;
import ir.maedehhz.final_project_spring.service.user_subservice.User_SubserviceServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final AdminServiceImpl adminService;
    private final ExpertServiceImpl expertService;
    private final ServiceServiceImpl serviceService;
    private final SubserviceServiceImpl subserviceService;
    private final User_SubserviceServiceImpl userSubserviceService;

    @PostMapping("/save-admin")
    public ResponseEntity<AdminSaveResponse> saveAdmin(@RequestBody AdminSaveRequest request){
        Admin admin = AdminMapper.INSTANCE.adminSaveRequestToModel(request);
        Admin saved = adminService.save(admin);

        AdminSaveResponse response = AdminMapper.INSTANCE.modelToAdminSaveResponse(saved);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/save-service")
    public ResponseEntity<ServiceSaveResponse> saveService(@RequestBody ServiceSaveRequest request){
        Service service = ServiceMapper.INSTANCE.serviceSaveRequestToModel(request);

        Service saved = serviceService.save(service);
        ServiceSaveResponse response = ServiceMapper.INSTANCE.modelToServiceSaveResponse(saved);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/save-subservice")
    public ResponseEntity<SubserviceSaveResponse> saveSubservice(@RequestBody SubserviceSaveRequest request){
        Subservice subservice = SubserviceMapper.INSTANCE.subserviceSaveRequestToModel(request);

        Subservice saved = subserviceService.save(subservice, request.serviceId());
        SubserviceSaveResponse response = SubserviceMapper.INSTANCE.modelToSubserviceSaveResponse(saved);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping("/verifying-expert")
    public ResponseEntity<ExpertSaveResponse> verifyingExpert(@RequestBody long expertId){
        Expert updated = expertService.updateStatusToVerified(expertId);

        ExpertSaveResponse response = ExpertMapper.INSTANCE.modelToExpertSaveResponse(updated);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/updating-expert-status-unverified")
    public ResponseEntity<ExpertSaveResponse> unverifyingExpert(@RequestBody long expertId){
        Expert updated = expertService.updateStatusToUnverified(expertId);

        ExpertSaveResponse response = ExpertMapper.INSTANCE.modelToExpertSaveResponse(updated);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/add-expert-to-subservice")
    public ResponseEntity<User_SubService> addExpertToSubservice(@RequestBody ExpertToSubserviceRequest request){
        User_SubService saved = userSubserviceService.save(request.expertId(), request.subserviceId());

        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PatchMapping("/remove-expert-from-subservice")
    public ResponseEntity<Boolean> removeExpertFromSubservice(@RequestBody ExpertToSubserviceRequest request){
        boolean removed = userSubserviceService.remove(request.subserviceId(), request.expertId());

        return new ResponseEntity<>(removed, HttpStatus.OK);
    }

    @PatchMapping("/update-subservice-description")
    public ResponseEntity<SubserviceSaveResponse> updateSubserviceDescription(@RequestBody SubserviceDescUpdateRequest request){
        Subservice updated = subserviceService.updateDescription(request.id(), request.newDescription());

        SubserviceSaveResponse response = SubserviceMapper.INSTANCE.modelToSubserviceSaveResponse(updated);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/update-subservice-base-price")
    public ResponseEntity<SubserviceSaveResponse> updateSubserviceBasePrice(@RequestBody SubserviceBasePriceUpdateRequest request){
        Subservice updated = subserviceService.updateBasePrice(request.id(), request.newBasePrice());

        SubserviceSaveResponse response = SubserviceMapper.INSTANCE.modelToSubserviceSaveResponse(updated);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/find-expert-by-id")
    public ResponseEntity<ExpertSaveResponse> findExpertById(@RequestBody long expertId){
        Expert expert = expertService.findById(expertId);

        ExpertSaveResponse response = ExpertMapper.INSTANCE.modelToExpertSaveResponse(expert);
        return new ResponseEntity<>(response, HttpStatus.FOUND);
    }

}
