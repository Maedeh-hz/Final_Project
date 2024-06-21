package ir.maedehhz.final_project_spring.model.custom_fields;

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
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String province;

    private String street;

    private String alley;

    private Integer no;

    public Address(String province, String street, String alley, Integer no) {
        this.province = province;
        this.street = street;
        this.alley = alley;
        this.no = no;
    }
}
