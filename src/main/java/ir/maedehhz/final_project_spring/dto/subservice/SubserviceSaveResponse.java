package ir.maedehhz.final_project_spring.dto.subservice;

import ir.maedehhz.final_project_spring.model.Service;

public record SubserviceSaveResponse(long id, Service service, String name,
                                     Double basePrice, String description) {
}
