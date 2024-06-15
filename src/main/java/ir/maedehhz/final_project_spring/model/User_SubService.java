package ir.maedehhz.final_project_spring.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SoftDelete;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@SoftDelete

@Entity
public class User_SubService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(cascade = {CascadeType.MERGE}, targetEntity = Expert.class)
    private Expert expert;

    @ManyToOne(cascade = {CascadeType.MERGE}, targetEntity = Subservice.class)
    private Subservice subservice;
}
