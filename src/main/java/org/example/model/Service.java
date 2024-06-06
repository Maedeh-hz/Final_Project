package org.example.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.example.base.model.BaseEntity;
import org.hibernate.annotations.SoftDelete;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@SuperBuilder
@SoftDelete

@Entity
public class Service extends BaseEntity<Long> {
    @Column(unique = true)
    private String serviceName;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Admin registeredAdmin;
}
