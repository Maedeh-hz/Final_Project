package ir.maedehhz.final_project_spring.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(insertable = false, updatable = false)
    private String dtype;

    private String firstName;

    private String lastName;

    private String email;

    @Column(unique = true)
    private String username;

    private String password;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Wallet wallet;

    private LocalDate registrationDate;
}
