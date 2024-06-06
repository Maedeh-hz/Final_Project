package org.example.base.service;

import org.example.base.model.BaseEntity;

import java.io.Serializable;

public interface BaseService<T extends BaseEntity<ID>, ID extends Serializable> {
    T saveOrUpdate(T entity);
    T findById(ID id);
    void delete(T entity);
}
