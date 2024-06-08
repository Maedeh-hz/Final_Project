package org.example.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.example.base.model.BaseEntity;
import org.hibernate.annotations.SoftDelete;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@SuperBuilder
@SoftDelete

@Entity
public class Subservice extends BaseEntity<Long> {
    @ManyToOne(cascade = CascadeType.MERGE)
    private Service service;

    @Column(unique = true)
    private String name;

    private Double basePrice;

    private String description;

    @ManyToMany(cascade = CascadeType.MERGE, targetEntity = Expert.class)
    private List<Expert> experts;

}
