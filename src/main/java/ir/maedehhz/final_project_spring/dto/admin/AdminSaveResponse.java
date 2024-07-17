package ir.maedehhz.final_project_spring.dto.admin;

import java.time.LocalDate;

public record AdminSaveResponse(Long id, String firstName, String lastName,
                                String email, LocalDate registrationDate) {

}
