package org.example.repository.user;

import org.example.base.repository.BaseRepository;
import org.example.model.User;

import java.util.Optional;

public interface UserRepository extends BaseRepository<User, Long> {
    Optional<String > findTypeByUsername(String username);
}
