package com.springboot.backend.proyecto1.service.impl;

import com.springboot.backend.proyecto1.model.Role;
import com.springboot.backend.proyecto1.model.User;
import com.springboot.backend.proyecto1.controller.request.RequestCreateUser;
import com.springboot.backend.proyecto1.repository.IUserRepository;
import com.springboot.backend.proyecto1.service.IRoleService;
import com.springboot.backend.proyecto1.service.IUserService;
import com.springboot.backend.proyecto1.exception.UserException;
import com.springboot.backend.proyecto1.enums.ERoleName;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements IUserService {

    private final IUserRepository userRepository;

    private final IRoleService roleService;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(IUserRepository userRepository, IRoleService roleService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Implementation method for save a User
     *
     * @param requestCreateUser - User data to create
     * @return {@link User}
     */
    @Override
    @Transactional
    public User save(RequestCreateUser requestCreateUser) {
        if (userRepository.existsByUsername(requestCreateUser.getUsername()))
            throw new UserException("El nombre de usuario ya está registrado");
        Set<Role> roles = new HashSet<>();
        roles.add(roleService.findByName(ERoleName.ROLE_USER));
        if (requestCreateUser.getRoles().contains("ROLE_ADMIN")) {
            roles.add(roleService.findByName(ERoleName.ROLE_ADMIN));
        }
        User user = new User();
        user.setUsername(requestCreateUser.getUsername());
        user.setPassword(passwordEncoder.encode(requestCreateUser.getPassword()));
        user.setActive(true);
        user.setRoles(roles);
        return userRepository.save(user);
    }

    /**
     * Implementation method for get a User by username
     */
    @Override
    @Transactional(readOnly = true)
    public User getUserByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) throw new UserException("El usuario no existe");
        return user.get();
    }

}
