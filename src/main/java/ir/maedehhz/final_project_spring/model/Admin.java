package ir.maedehhz.final_project_spring.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SoftDelete;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@SoftDelete

@Entity
public class Admin extends User{
}
