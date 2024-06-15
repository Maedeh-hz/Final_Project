package ir.maedehhz.final_project_spring.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SoftDelete;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@SoftDelete

@Entity
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private Double balance;
}
