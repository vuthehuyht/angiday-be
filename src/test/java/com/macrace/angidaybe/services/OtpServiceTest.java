package com.macrace.angidaybe.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
public class OtpServiceTest {
    @Autowired
    private OtpService otpService;

    @Test
    void otpServiceInitializedCorrectly() {
        assertThat(otpService).isNotNull();
    }
}
