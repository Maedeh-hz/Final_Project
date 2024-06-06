package org.example.model;

import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SoftDelete;

@AllArgsConstructor
@ToString
@SuperBuilder
@SoftDelete

@Entity
public class Admin extends User {

}
