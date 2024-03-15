package com.macrace.angidaybe.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.macrace.angidaybe.dto.request.AuthRequest;
import com.macrace.angidaybe.dto.request.RegisterRequest;
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

    private void createDataTest() {
        RegisterRequest request = new RegisterRequest();
        request.setUsername("0123456789");
        request.setPassword("12345678");
        request.setConfirmPassword("12345678");
        request.setFullName("test");

        authService.register(request);
    }
}
