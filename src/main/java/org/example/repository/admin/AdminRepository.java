package org.example.repository.admin;

import org.example.base.repository.BaseRepository;
import org.example.model.Admin;

import java.util.Optional;

public interface AdminRepository extends BaseRepository<Admin, Long> {
    Optional<Admin> findByUsername(String username);
}
