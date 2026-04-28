package com.chakray.api.controller;

import com.chakray.api.config.TestConfig;
import com.chakray.api.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
@Import(TestConfig.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService service;

    @Test
    void shouldLoginSuccessfully() throws Exception {

        Mockito.when(service.loginAndGenerateToken("AARR990101XXX", "password"))
                .thenReturn("token123");

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                          "tax_id": "AARR990101XXX",
                          "password": "password"
                        }
                        """))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnUnauthorized() throws Exception {

        Mockito.when(service.loginAndGenerateToken(any(), any()))
                .thenReturn(null);

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                          "tax_id": "bad",
                          "password": "bad"
                        }
                        """))
                .andExpect(status().isUnauthorized());
    }
}