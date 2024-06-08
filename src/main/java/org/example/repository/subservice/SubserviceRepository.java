package org.example.repository.subservice;

import org.example.base.repository.BaseRepository;
import org.example.model.Subservice;

import java.util.List;
import java.util.Optional;

public interface SubserviceRepository extends BaseRepository<Subservice, Long> {
    Optional<List<Subservice>> loadAll();
}
