package org.example.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.example.base.model.BaseEntity;
import org.hibernate.annotations.SoftDelete;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@SuperBuilder
@SoftDelete

@Entity
@Table(name = "users")
public class User extends BaseEntity<Long> {
    @Column(insertable = false, updatable = false)
    private String dtype;

    private String firstName;

    private String lastName;

    @Email
    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String username;

    private String password;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Wallet wallet;

    private LocalDate registrationDate;


}
