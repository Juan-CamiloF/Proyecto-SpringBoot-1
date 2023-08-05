package com.springboot.backend.proyecto1.repository;

import com.springboot.backend.proyecto1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Interface of {@link User} for CRUD operations
 */
public interface IUserRepository extends JpaRepository<User, Long> {

    /**
     * Optional User by username
     */
    Optional<User> findByUsername(String username);

    /**
     * User exists by username
     */
    boolean existsByUsername(String username);
}
