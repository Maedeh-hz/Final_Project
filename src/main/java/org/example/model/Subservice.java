package org.example.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.example.base.model.BaseEntity;
import org.hibernate.annotations.SoftDelete;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@SuperBuilder
@SoftDelete

@Entity
public class Subservice extends BaseEntity<Long> {
    @ManyToOne(cascade = CascadeType.MERGE)
    private Service service;

    @Column(unique = true)
    private String name;

    @ManyToOne(cascade = CascadeType.MERGE, targetEntity = Expert.class)
    private Expert expert;

    private Double basePrice;

    private String description;

}
