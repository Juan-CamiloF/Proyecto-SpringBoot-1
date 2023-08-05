package com.springboot.backend.proyecto1.repository;

import com.springboot.backend.proyecto1.data.UserData;
import com.springboot.backend.proyecto1.model.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@DataJpaTest
class RoleRepositoryTest {

    @Autowired
    IRoleRepository roleRepository;

    @Test
    void findRoleByName() {
        Role role = roleRepository.save(UserData.ROLE());
        Optional<Role> test = roleRepository.findByName(UserData.ROLE().getName());
        assertTrue(test.isPresent());
        assertEquals(role, test.get());
    }
}
