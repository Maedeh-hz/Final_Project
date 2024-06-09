package org.example.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.example.base.model.BaseEntity;
import org.hibernate.annotations.SoftDelete;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@SuperBuilder
@SoftDelete

@Entity
public class User_SubService extends BaseEntity<Long> {
    @ManyToOne(cascade = {CascadeType.MERGE}, targetEntity = Expert.class)
    private Expert expert;

    @ManyToOne(cascade = {CascadeType.MERGE}, targetEntity = Subservice.class)
    private Subservice subservice;
}
