package com.springboot.backend.proyecto1.service;

import com.springboot.backend.proyecto1.model.User;
import com.springboot.backend.proyecto1.controller.request.RequestCreateUser;

public interface IUserService {

    /**
     * Save a User
     */
    User save(RequestCreateUser requestCreateUser);

    /**
     * Get a User by username
     */
    User getUserByUsername(String username);

}
