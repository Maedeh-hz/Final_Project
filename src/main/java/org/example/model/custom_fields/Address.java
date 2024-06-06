package org.example.model.custom_fields;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.example.base.model.BaseEntity;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@SuperBuilder

@Entity
public class Address extends BaseEntity<Long> {
    private String province;

    private String street;

    private String alley;

    private Integer no;
}
