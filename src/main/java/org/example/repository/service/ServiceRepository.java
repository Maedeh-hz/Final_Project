package org.example.repository.service;

import org.example.base.repository.BaseRepository;
import org.example.model.Service;

import java.util.List;
import java.util.Optional;

public interface ServiceRepository extends BaseRepository<Service, Long> {
    Optional<List<Service>> loadAll();
}
