package com.springboot.backend.proyecto1.repository;

import com.springboot.backend.proyecto1.model.Role;
import com.springboot.backend.proyecto1.enums.ERoleName;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Interface of {@link Role} for CRUD operations
 */
public interface IRoleRepository extends CrudRepository<Role, Long> {

    /**
     * Optional Role by name
     */
    Optional<Role> findByName(ERoleName name);
}
