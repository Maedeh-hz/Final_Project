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
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(unique = true)
    private String serviceName;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Admin registeredAdmin;

}
