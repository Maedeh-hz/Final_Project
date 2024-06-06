package org.example.model.custom_fields;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.example.base.model.BaseEntity;

import java.io.File;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder

@Entity
public class Image extends BaseEntity<Long> {
    private String name;
    private File image;
}
