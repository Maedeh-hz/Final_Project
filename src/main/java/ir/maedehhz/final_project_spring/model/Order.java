package ir.maedehhz.final_project_spring.model;

import ir.maedehhz.final_project_spring.model.custom_fields.Address;
import ir.maedehhz.final_project_spring.model.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SoftDelete;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@SoftDelete

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Subservice subservice;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Customer customer;

    private String description;

    private Double suggestingPrice;

    private LocalDateTime registerDate;

    private LocalDateTime toDoDateAndTime;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToOne(cascade = CascadeType.MERGE)
    private Suggestion suggestion;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Address address;
}
