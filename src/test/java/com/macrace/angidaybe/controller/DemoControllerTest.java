package com.macrace.angidaybe.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class DemoControllerTest {
    @Autowired
    private DemoController demoController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void demoControllerInitializedCorrectly() {
        assertThat(demoController).isNotNull();
    }

    @Test
    void testReturn200() throws Exception {
        mockMvc.perform(get("/api/v1/demo")).andExpect(status().isOk());
    }

    @Test
    void testReturnDemoMessage() throws Exception {
        mockMvc.perform(get("/api/v1/demo")).andExpect(content().string(containsString("API demo is running")));
    }
}
