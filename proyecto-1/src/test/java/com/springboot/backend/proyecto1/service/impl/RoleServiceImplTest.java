package com.springboot.backend.proyecto1.service.impl;

import com.springboot.backend.proyecto1.data.UserData;
import com.springboot.backend.proyecto1.enums.ERoleName;
import com.springboot.backend.proyecto1.exception.RoleException;
import com.springboot.backend.proyecto1.model.Role;
import com.springboot.backend.proyecto1.repository.IRoleRepository;
import com.springboot.backend.proyecto1.service.IRoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest {

    @Mock
    IRoleRepository roleRepository;

    IRoleService roleService;

    @BeforeEach
    void init() {
        roleService = new RoleServiceImpl(roleRepository);
    }

    @Test
    void testFindByName() {
        Role role = UserData.ROLE();
        when(roleRepository.findByName(ERoleName.ROLE_USER)).thenReturn(Optional.of(role));
        Role test = roleService.findByName(ERoleName.ROLE_USER);
        assertEquals(role, test);
        verify(roleRepository).findByName(ERoleName.ROLE_USER);
    }

    @Test
    void testFindByNonexistentName() {
        when(roleRepository.findByName(ERoleName.ROLE_USER)).thenReturn(Optional.empty());
        assertThrows(RoleException.class, () -> roleService.findByName(ERoleName.ROLE_USER));
        verify(roleRepository).findByName(ERoleName.ROLE_USER);
    }
}
