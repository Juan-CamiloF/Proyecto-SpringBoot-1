package com.springboot.backend.proyecto1.data;

import com.springboot.backend.proyecto1.enums.ERoleName;
import com.springboot.backend.proyecto1.model.Role;
import com.springboot.backend.proyecto1.model.User;

import java.util.Set;

public class UserData {

    public static Role ROLE() {
        Role role = new Role();
        role.setId(1L);
        role.setName(ERoleName.ROLE_USER);
        return role;
    }

    public static Role ROLE_ADMIN() {
        Role role = new Role();
        role.setId(2L);
        role.setName(ERoleName.ROLE_ADMIN);
        return role;
    }

    public static User USER() {
        User user = new User();
        user.setId(1L);
        user.setUsername("USERNAME");
        user.setPassword("PASSWORD");
        user.setActive(Boolean.TRUE);
        user.setRoles(Set.of(ROLE(), ROLE_ADMIN()));
        return user;
    }
}
