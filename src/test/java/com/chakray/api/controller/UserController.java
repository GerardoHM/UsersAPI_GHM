package com.chakray.api.controller;

import com.chakray.api.dto.UserRequestDTO;
import com.chakray.api.dto.UserResponseDTO;
import com.chakray.api.model.User;
import com.chakray.api.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@Import(UserControllerTest.TestConfig.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService service;

    @Autowired
    private ObjectMapper objectMapper;

    // 🔧 MOCK manual del service (sin @MockBean)
    @TestConfiguration
    static class TestConfig {
        @Bean
        public UserService userService() {
            return Mockito.mock(UserService.class);
        }
    }

    // -----------------------------
    // GET /users
    // -----------------------------
    @Test
    void shouldGetUsers() throws Exception {

        List<User> users = List.of(new User(), new User());

        Mockito.when(service.getUsers(any(), any()))
                .thenReturn(users);

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk());
    }

    // -----------------------------
    // POST /users
    // -----------------------------
    @Test
    void shouldCreateUser() throws Exception {

        UserRequestDTO dto = new UserRequestDTO();
        dto.setEmail("test@mail.com");
        dto.setName("John Doe");
        dto.setPassword("password123");
        dto.setPhone("+525512345678");
        dto.setTaxId("ABCD990101XYZ");

        UserResponseDTO response = new UserResponseDTO();

        Mockito.when(service.create(any(UserRequestDTO.class)))
                .thenReturn(response);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());
    }

    // -----------------------------
    // PATCH /users/{id}
    // -----------------------------
    @Test
    void shouldUpdateUser() throws Exception {

        UUID id = UUID.randomUUID();

        User user = new User();

        Mockito.when(service.update(eq(id), any()))
                .thenReturn(user);

        mockMvc.perform(patch("/users/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {
                          "name": "updated"
                        }
                        """))
                .andExpect(status().isOk());
    }

    // -----------------------------
    // DELETE /users/{id}
    // -----------------------------
    @Test
    void shouldDeleteUser() throws Exception {

        UUID id = UUID.randomUUID();

        Mockito.doNothing().when(service).delete(id);

        mockMvc.perform(delete("/users/" + id))
                .andExpect(status().isOk());
    }
}
