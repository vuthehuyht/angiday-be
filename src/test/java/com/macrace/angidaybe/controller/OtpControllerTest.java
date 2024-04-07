package com.macrace.angidaybe.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.macrace.angidaybe.dto.request.OtpRequest;
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
public class OtpControllerTest {
    @Autowired
    private OtpController otpController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void otpControllerInitializedCorrectly() {
        assertThat(otpController).isNotNull();
    }

    @Test
    void testWithAllBlank() throws Exception {
        OtpRequest request = new OtpRequest();

        mockMvc.perform(post("/api/v1/otp").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testWithPhoneNumberBlank() throws Exception {
        OtpRequest request = new OtpRequest();
        request.setOtpCode("1234");

        mockMvc.perform(post("/api/v1/otp").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]").value("Phone number is required"));
    }

    @Test
    void testWithOtpCodeBlank() throws Exception {
        OtpRequest request = new OtpRequest();
        request.setPhoneNumber("01234567890");

        mockMvc.perform(post("/api/v1/otp").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0]").value("OTP Code is required"));
    }
}
