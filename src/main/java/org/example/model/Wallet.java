package org.example.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.example.base.model.BaseEntity;
import org.hibernate.annotations.SoftDelete;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString
@SoftDelete

@Entity
public class Wallet extends BaseEntity<Long> {
    private Double balance;
}
