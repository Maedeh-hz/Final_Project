package org.example.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.example.base.model.BaseEntity;
import org.example.model.custom_fields.Address;
import org.hibernate.annotations.SoftDelete;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@SoftDelete

@Entity
@Table(name = "orders")
public class Order extends BaseEntity<Long> {
    @ManyToOne(cascade = CascadeType.MERGE)
    private Subservice subservice;

    private String description;

    private Double suggestingPrice;

    private LocalDate registerDate;

    private LocalDate toDoDateAndTime;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Address address;
}
