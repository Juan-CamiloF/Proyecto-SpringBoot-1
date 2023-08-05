package com.springboot.backend.proyecto1.repository;

import com.springboot.backend.proyecto1.data.UserData;
import com.springboot.backend.proyecto1.model.Role;
import com.springboot.backend.proyecto1.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    IUserRepository userRepository;

    @Autowired
    IRoleRepository roleRepository;

    @Test
    void testSaveAUser() {
        User user = UserData.USER();
        Role role = roleRepository.save(UserData.ROLE());
        user.setRoles(null);
        User test = userRepository.save(user);
        assertNotNull(test);
        assertEquals(user.getUsername(), test.getUsername());
        assertEquals(user.getPassword(), test.getPassword());
    }

    @Test
    void testSaveAUserWithUsernameInUse() {
        User user = UserData.USER();
        Role role = roleRepository.save(UserData.ROLE());
        user.setRoles(Set.of(role));
        userRepository.save(user);
        user.setId(null);
        assertThrows(DataIntegrityViolationException.class, () -> userRepository.save(user));
    }

    @Test
    void testGetUserByUserName() {
        User user = UserData.USER();
        Role role = roleRepository.save(UserData.ROLE());
        user.setRoles(Set.of(role));
        userRepository.save(user);
        Optional<User> test = userRepository.findByUsername(user.getUsername());
        assertTrue(test.isPresent());
        assertEquals(user.getUsername(), test.get().getUsername());
        assertEquals(user.getPassword(), test.get().getPassword());
    }
}
