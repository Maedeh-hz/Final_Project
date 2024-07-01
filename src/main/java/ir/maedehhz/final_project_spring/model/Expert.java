package ir.maedehhz.final_project_spring.model;

import ir.maedehhz.final_project_spring.model.enums.ExpertStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SoftDelete;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@SoftDelete

@Entity
public class Expert extends User {

    @Enumerated(EnumType.STRING)
    private ExpertStatus status;

    private Double score;

    private String expertise;

    @Column(name = "image_data", columnDefinition = "bytea")
    private byte[] image;
}
