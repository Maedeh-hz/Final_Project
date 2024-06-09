package org.example.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.example.base.model.BaseEntity;
import org.example.model.custom_fields.Address;
import org.example.model.enums.OrdersLevel;
import org.hibernate.annotations.SoftDelete;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@SoftDelete

@Entity
@Table(name = "orders")
public class Order extends BaseEntity<Long> {
    @ManyToOne(cascade = CascadeType.MERGE)
    private Subservice subservice;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Customer customer;

    private String description;

    private Double suggestingPrice;

    private LocalDate registerDate;

    private LocalDateTime toDoDateAndTime;

    @Enumerated(EnumType.STRING)
    private OrdersLevel ordersLevel;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Address address;
}
