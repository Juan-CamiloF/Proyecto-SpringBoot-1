package com.springboot.backend.proyecto1.service.impl;

import com.springboot.backend.proyecto1.model.Role;
import com.springboot.backend.proyecto1.repository.IRoleRepository;
import com.springboot.backend.proyecto1.service.IRoleService;
import com.springboot.backend.proyecto1.exception.RoleException;
import com.springboot.backend.proyecto1.enums.ERoleName;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements IRoleService {

    private final IRoleRepository roleRepository;

    public RoleServiceImpl(IRoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findByName(ERoleName name) {
        return roleRepository.findByName(name).orElseThrow(() -> new RoleException(String.format("Rol no encontrado %s", name.toString())));
    }
}
