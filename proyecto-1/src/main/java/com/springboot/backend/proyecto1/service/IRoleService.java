package com.springboot.backend.proyecto1.service;

import com.springboot.backend.proyecto1.model.Role;
import com.springboot.backend.proyecto1.enums.ERoleName;

public interface IRoleService {

    /**
     * Get Role by Name
     */
    Role findByName(ERoleName name);
}
