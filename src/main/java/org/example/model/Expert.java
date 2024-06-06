package org.example.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.example.model.custom_fields.Image;
import org.example.model.enums.ExpertsLevel;
import org.hibernate.annotations.SoftDelete;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@SoftDelete

@Entity
public class Expert extends User{
    @Enumerated(EnumType.STRING)
    private ExpertsLevel expertsLevel;

    private Double score;

//    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    private Image image;

}
