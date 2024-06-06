package org.example.base.repository;

import org.example.base.model.BaseEntity;

import java.io.Serializable;
import java.util.Optional;

public interface BaseRepository<T extends BaseEntity<ID>, ID extends Serializable> {
    T saveOrUpdate(T entity);
    Optional<T> findById(ID id);
    void delete(T entity);
}
