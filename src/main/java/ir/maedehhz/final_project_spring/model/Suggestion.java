package ir.maedehhz.final_project_spring.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SoftDelete;

import java.time.LocalDate;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@SoftDelete

@Entity
public class Suggestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Expert expert;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Order order;

    private String description;

    private LocalDate registerDate;

    private Double price;

    private Double workDuration;
}
