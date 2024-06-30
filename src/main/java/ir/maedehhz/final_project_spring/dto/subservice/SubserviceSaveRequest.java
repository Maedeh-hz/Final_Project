package ir.maedehhz.final_project_spring.dto.subservice;

public record SubserviceSaveRequest(long serviceId, String name,
                                    double basePrice, String description) {
}
