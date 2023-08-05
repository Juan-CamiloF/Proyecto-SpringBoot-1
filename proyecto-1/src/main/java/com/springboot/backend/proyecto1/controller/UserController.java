package com.springboot.backend.proyecto1.controller;

import com.springboot.backend.proyecto1.controller.request.RequestAuthUser;
import com.springboot.backend.proyecto1.controller.request.RequestCreateUser;
import com.springboot.backend.proyecto1.controller.response.ResponseAuthUser;
import com.springboot.backend.proyecto1.controller.response.ResponseCreateUser;
import com.springboot.backend.proyecto1.controller.response.ResponseUserData;
import com.springboot.backend.proyecto1.model.User;
import com.springboot.backend.proyecto1.security.jwt.JwtProvider;
import com.springboot.backend.proyecto1.service.IUserService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Controller for {@link User}
 */
@RestController
@RequestMapping("/api/v1/auth")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final AuthenticationManager authenticationManager;

    private final JwtProvider jwtProvider;

    private static final String RESPONSE = "Respuesta: {}";
    private static final String USER_CREATED_MESSAGE = "Usuario creado éxitosamente";

    private final IUserService userService;

    public UserController(AuthenticationManager authenticationManager, JwtProvider jwtProvider, IUserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.userService = userService;
    }

    /**
     * Controller to authenticate {@link User}
     *
     * @param requestAuthUser -> auth info
     * @return Jwt
     */
    @ApiOperation("Petición para iniciar sesión")
    @PostMapping
    public ResponseEntity<ResponseAuthUser> authUser(@Valid @RequestBody RequestAuthUser requestAuthUser) {
        logger.info("Petición para iniciar sesión {} ", requestAuthUser);
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(requestAuthUser.getUsername(), requestAuthUser.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        ResponseAuthUser response = new ResponseAuthUser(jwt);
        logger.info("Token para {} , {}", requestAuthUser, response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Controller to get token information
     *
     * @return UserDetails
     */
    @ApiOperation("Petición para obtener información del token")
    @GetMapping
    public ResponseEntity<ResponseUserData> getUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.getUserByUsername(userDetails.getUsername());
        ResponseUserData response = new ResponseUserData(user);
        logger.info("Petición para obtener información {}", response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Controller to create a {@link User}
     *
     * @param requestCreateUser -> user entity
     * @return User
     */
    @ApiOperation("Petición para crear un nuevo usuario")
    @PostMapping("/new")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ResponseCreateUser> createUser(@Valid @RequestBody RequestCreateUser requestCreateUser) {
        logger.info("Petición para crear un nuevo usuario {}", requestCreateUser);
        User user = userService.save(requestCreateUser);
        ResponseCreateUser response = new ResponseCreateUser(USER_CREATED_MESSAGE, user);
        logger.info(RESPONSE, response);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
