package org.example.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.example.model.enums.ExpertsLevel;
import org.hibernate.annotations.SoftDelete;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@SoftDelete

@Entity
public class Expert extends User{
    @Enumerated(EnumType.STRING)
    private ExpertsLevel expertsLevel;

    private Double score;

    @ManyToMany(cascade = CascadeType.MERGE, targetEntity = Subservice.class,
            mappedBy = "experts")
    private List<Subservice> subservice;

    @Column(name = "image_data", columnDefinition = "bytea")
    private byte[] image;
}
