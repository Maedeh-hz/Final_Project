package ir.maedehhz.final_project_spring.dto.admin;

import java.time.LocalDate;

public record AdminSaveResponse(long id, String firstName, String lastName,
                                String email, String username,
                                LocalDate registrationDate) {

}
