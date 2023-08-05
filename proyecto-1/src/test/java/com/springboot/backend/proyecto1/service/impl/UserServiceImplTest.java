package com.springboot.backend.proyecto1.service.impl;

import com.springboot.backend.proyecto1.controller.request.RequestCreateUser;
import com.springboot.backend.proyecto1.data.UserData;
import com.springboot.backend.proyecto1.enums.ERoleName;
import com.springboot.backend.proyecto1.exception.UserException;
import com.springboot.backend.proyecto1.model.Role;
import com.springboot.backend.proyecto1.model.User;
import com.springboot.backend.proyecto1.repository.IUserRepository;
import com.springboot.backend.proyecto1.service.IRoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    IUserRepository userRepository;

    @Mock
    IRoleService roleService;

    @Mock
    PasswordEncoder passwordEncoder;

    UserServiceImpl userService;

    @BeforeEach
    void init() {
        userService = new UserServiceImpl(userRepository, roleService, passwordEncoder);
    }

    @Test
    void testSaveAUser() {
        User user = UserData.USER();
        Role role = UserData.ROLE();
        when(userRepository.existsByUsername(anyString())).thenReturn(Boolean.FALSE);
        when(roleService.findByName(ERoleName.ROLE_USER)).thenReturn(role);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(passwordEncoder.encode(anyString())).thenReturn(user.getPassword());
        RequestCreateUser requestCreateUser = new RequestCreateUser();
        requestCreateUser.setUsername(user.getUsername());
        requestCreateUser.setRoles(Set.of(UserData.ROLE().getName().toString()));
        requestCreateUser.setPassword(user.getPassword());
        User test = userService.save(requestCreateUser);
        assertNotNull(test);
        assertEquals(user, test);
        verify(userRepository).existsByUsername(anyString());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testSaveAUserAdmin() {
        User user = UserData.USER();
        Role role = UserData.ROLE();
        Role roleAdmin = UserData.ROLE_ADMIN();
        when(userRepository.existsByUsername(anyString())).thenReturn(Boolean.FALSE);
        when(roleService.findByName(ERoleName.ROLE_USER)).thenReturn(role);
        when(roleService.findByName(ERoleName.ROLE_ADMIN)).thenReturn(roleAdmin);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(passwordEncoder.encode(anyString())).thenReturn(user.getPassword());
        RequestCreateUser requestCreateUser = new RequestCreateUser();
        requestCreateUser.setUsername(user.getUsername());
        requestCreateUser.setRoles(Set.of(UserData.ROLE().getName().toString(), UserData.ROLE_ADMIN().getName().toString()));
        requestCreateUser.setPassword(user.getPassword());
        User test = userService.save(requestCreateUser);
        assertNotNull(test);
        assertEquals(user, test);
        verify(userRepository).existsByUsername(anyString());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void testSaveAUserWithUsernameInUse() {
        User user = UserData.USER();
        when(userRepository.existsByUsername(anyString())).thenReturn(Boolean.TRUE);
        RequestCreateUser requestCreateUser = new RequestCreateUser();
        requestCreateUser.setUsername(user.getUsername());
        requestCreateUser.setPassword(user.getPassword());
        requestCreateUser.setRoles(Set.of(UserData.ROLE().getName().toString()));
        assertThrows(UserException.class, () -> userService.save(requestCreateUser));
        verify(userRepository).existsByUsername(anyString());
    }

    @Test
    void testGetUserByUsername() {
        User user = UserData.USER();
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        User test = userService.getUserByUsername(user.getUsername());
        assertNotNull(test);
        assertEquals(user, test);
        verify(userRepository).findByUsername(user.getUsername());
    }

    @Test
    void testGetUserByNonexistentUsername() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        String username = anyString();
        assertThrows(UserException.class, () -> userService.getUserByUsername(username));
        verify(userRepository).findByUsername(anyString());
    }


}
