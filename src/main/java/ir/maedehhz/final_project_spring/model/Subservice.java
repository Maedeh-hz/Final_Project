package ir.maedehhz.final_project_spring.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Subservice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Service service;

    @Column(unique = true)
    private String name;

    private Double basePrice;

    private String description;
}
