package org.example.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.example.base.model.BaseEntity;
import org.hibernate.annotations.SoftDelete;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString
@SoftDelete

@Entity
public class Suggestion extends BaseEntity<Long> {
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Expert expert;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Order order;

    private String description;

    private LocalDate registerDate;

    private Double price;

    private Double workDuration;
}
