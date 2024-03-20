package com.macrace.angidaybe.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.macrace.angidaybe.dto.request.ForgotPasswordRequest;
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
public class UserControllerTest {
    @Autowired
    private UserController userController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void userControllerInitializedCorrectly() {
        assertThat(userController).isNotNull();
    }

    @Test
    void testPhoneNumberBlank() throws Exception {
        ForgotPasswordRequest request = new ForgotPasswordRequest();
        request.setPhoneNumber("");

        mockMvc.perform(post("/api/v1/user/forget-password").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]").value("Phone number is required"));
        ;
    }

    @Test
    void testPhoneNumberNotFormat() throws Exception {
        ForgotPasswordRequest request = new ForgotPasswordRequest();
        request.setPhoneNumber("01123");

        mockMvc.perform(post("/api/v1/user/forget-password").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]").value("Phone number wrong"));
    }
}
