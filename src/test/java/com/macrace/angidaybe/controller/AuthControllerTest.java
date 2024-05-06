package com.macrace.angidaybe.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.macrace.angidaybe.dto.request.AuthRequest;
import com.macrace.angidaybe.dto.request.RegisterRequest;
import com.macrace.angidaybe.models.Role;
import com.macrace.angidaybe.repositories.RoleRepository;
import com.macrace.angidaybe.services.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AuthControllerTest {
    @Autowired
    private AuthController authController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AuthService authService;

    @Autowired
    private RoleRepository roleRepository;

    @Test
    void authControllerInitializedCorrectly() {
        assertThat(authController).isNotNull();
    }

    @Test
    void testValidLogin() throws Exception {
        createDataTest();

        AuthRequest request = new AuthRequest();
        request.setUsername("0123456789");
        request.setPassword("12345678");

        mockMvc.perform(post("/api/v1/auth/login").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isOk());
    }

    @Test
    void testLoginWitUsernameBlank() throws Exception {
        AuthRequest request = new AuthRequest();
        request.setUsername("");
        request.setPassword("12345678");

        mockMvc.perform(post("/api/v1/auth/login").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testLoginWitPasswordBlank() throws Exception {
        AuthRequest request = new AuthRequest();
        request.setUsername("0123456789");
        request.setPassword("");

        mockMvc.perform(post("/api/v1/auth/login").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testLoginWitUsernameAndPasswordBlank() throws Exception {
        AuthRequest request = new AuthRequest();
        request.setUsername("");
        request.setPassword("");

        mockMvc.perform(post("/api/v1/auth/login").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testLoginWitUsernameNotFound() throws Exception {
        createDataTest();

        AuthRequest request = new AuthRequest();
        request.setUsername("0123456780");
        request.setPassword("12345678");

        mockMvc.perform(post("/api/v1/auth/login").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("404"))
                .andExpect(jsonPath("$.message").value("Username not found"));
    }

    @Test
    void testLoginWitPasswordWrong() throws Exception {
        createDataTest();

        AuthRequest request = new AuthRequest();
        request.setUsername("0123456789");
        request.setPassword("1234567890");

        mockMvc.perform(post("/api/v1/auth/login").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(601))
                .andExpect(jsonPath("$.message").value("Username or Password incorrect"));
        ;
    }

    @Test
    void testRegisterValidRequest() throws Exception {
        Role role = Role.builder()
                .name("ROLE_USER")
                .build();
        roleRepository.save(role);

        RegisterRequest request = new RegisterRequest();
        request.setUsername("0123456789");
        request.setPassword("12345678");
        request.setFullName("test");

        mockMvc.perform(post("/api/v1/auth/register").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isOk());
    }

    @Test
    void testRegisterWithUsernameBlank() throws Exception {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("");
        request.setPassword("12345678");
        request.setFullName("test");

        mockMvc.perform(post("/api/v1/auth/register").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Username is required"));
    }

    @Test
    void testRegisterWithPasswordBlank() throws Exception {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("0123456789");
        request.setPassword("");
        request.setFullName("test");

        mockMvc.perform(post("/api/v1/auth/register").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testRegisterWithDuplicateUsername() throws Exception {
        createDataTest();

        RegisterRequest request = new RegisterRequest();
        request.setUsername("0123456789");
        request.setPassword("12345678");
        request.setFullName("test 1");

        mockMvc.perform(post("/api/v1/auth/register").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(600))
                .andExpect(jsonPath("$.message").value("Phone number exist"));
    }

    @Test
    void testRegisterWithPasswordAtLeast8Character() throws Exception {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("0123456789");
        request.setPassword("123456");
        request.setFullName("test");

        mockMvc.perform(post("/api/v1/auth/register").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(606))
                .andExpect(jsonPath("$.message").value("Password at least 8 characters"));
    }

    private void createDataTest() {
        Role role = Role.builder()
                .name("ROLE_USER")
                .build();
        roleRepository.save(role);

        RegisterRequest request = new RegisterRequest();
        request.setUsername("0123456789");
        request.setPassword("12345678");
        request.setFullName("test");

        authService.register(request);
    }
}
