package com.springboot.backend.proyecto1.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.springboot.backend.proyecto1.controller.request.RequestAuthUser;
import com.springboot.backend.proyecto1.controller.request.RequestCreateUser;
import com.springboot.backend.proyecto1.controller.response.ResponseAuthUser;
import com.springboot.backend.proyecto1.data.UserData;
import com.springboot.backend.proyecto1.model.User;
import com.springboot.backend.proyecto1.security.jwt.JwtProvider;
import com.springboot.backend.proyecto1.security.service.UserDetailsServiceImpl;
import com.springboot.backend.proyecto1.service.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(value = UserController.class)
class UserControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    IUserService userService;

    @MockBean
    AuthenticationManager authenticationManager;

    @MockBean
    UserDetailsServiceImpl userDetails;

    @Autowired
    private UserController userController;

    @MockBean
    JwtProvider jwtProvider;


    @Test
    void testAuthUserController() {
        User user = UserData.USER();
        RequestAuthUser requestAuthUser = new RequestAuthUser();
        requestAuthUser.setUsername(user.getUsername());
        requestAuthUser.setPassword(user.getPassword());
        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(jwtProvider.generateToken(any())).thenReturn("jwt_token");
        SecurityContextHolder.getContext().setAuthentication(authentication);

        ResponseEntity<ResponseAuthUser> responseEntity = userController.authUser(requestAuthUser);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().getJwt()).isEqualTo("jwt_token");
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    void testCreateAUser() throws Exception {
        User user = UserData.USER();
        RequestCreateUser requestCreateUser = new RequestCreateUser();
        requestCreateUser.setUsername(UserData.USER().getUsername());
        requestCreateUser.setPassword(UserData.USER().getPassword());
        requestCreateUser.setRoles(Set.of(String.valueOf(UserData.ROLE().getName()), String.valueOf(UserData.ROLE_ADMIN().getName())));
        String string = "{\"username\":\"USERNAME\",\"password\":\"PASSWORD\",\"roles\":[\"ROLE_USER\",\"ROLE_ADMIN\"]}";
        when(userService.save(any(RequestCreateUser.class))).thenReturn(user);
        mvc.perform(post("/api/v1/auth/new").with(csrf()).content(string).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.message").value("Usuario creado éxitosamente")).andExpect(jsonPath("$.id").value(user.getId())).andExpect(jsonPath("$.username").value(user.getUsername())).andExpect(jsonPath("$.active").value(Boolean.TRUE)).andExpect(jsonPath("$.roles").isArray()).andReturn();
    }

    @Test
    void testUnauthenticatedCreateAUser() throws Exception {
        User user = UserData.USER();
        RequestCreateUser requestCreateUser = new RequestCreateUser();
        requestCreateUser.setUsername(UserData.USER().getUsername());
        requestCreateUser.setPassword(UserData.USER().getPassword());
        requestCreateUser.setRoles(Set.of(String.valueOf(UserData.ROLE().getName()), String.valueOf(UserData.ROLE_ADMIN().getName())));
        String string = "{\"username\":\"USERNAME\",\"password\":\"PASSWORD\",\"roles\":[\"ROLE_USER\",\"ROLE_ADMIN\"]}";
        when(userService.save(any(RequestCreateUser.class))).thenReturn(user);
        mvc.perform(post("/api/v1/auth/new").content(string).with(csrf()).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isUnauthorized());
    }


    @Test
    @WithMockUser
    void testGetUser() throws Exception {
        User user = UserData.USER();
        when(userService.getUserByUsername(anyString())).thenReturn(user);
        mvc.perform(get("/api/v1/auth").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(jsonPath("$.id").value(user.getId())).andExpect(jsonPath("$.username").value(user.getUsername())).andExpect(jsonPath("$.active").value(user.isActive())).andExpect(jsonPath("$.roles").isArray());
    }

    private String asJsonString(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return objectMapper.writeValueAsString(obj);
    }

}
