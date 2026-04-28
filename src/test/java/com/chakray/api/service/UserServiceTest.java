package com.chakray.api.service;

import com.chakray.api.dto.UserRequestDTO;
import com.chakray.api.dto.UserResponseDTO;
import com.chakray.api.exception.CustomException;
import com.chakray.api.mapper.UserMapper;
import com.chakray.api.model.Address;
import com.chakray.api.model.User;
import com.chakray.api.security.JwtFilter;
import com.chakray.api.util.AESUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(org.mockito.junit.jupiter.MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService service;

    @Mock
    private UserMapper mapper;

    @Mock
    private JwtService jwtService;

    @Test
    void shouldCreateUserSuccessfully() {
        UserRequestDTO dto = new UserRequestDTO();
        dto.setTaxId("TEST123456XXX");
        dto.setPassword("password");

        var response = service.create(dto);

        assertNotNull(response);
    }

    @Test
    void shouldLoginAndGenerateToken() {
        User user = new User();
        user.setTaxId("AARR990101XXX");
        user.setPassword(AESUtil.encrypt("password"));

        service.getUsers().add(user);

        when(jwtService.generateToken(any())).thenReturn("fake-token");

        String token = service.loginAndGenerateToken("AARR990101XXX", "password");

        assertNotNull(token);
        assertEquals("fake-token", token);
    }

    @Test
    void shouldReturnNullWhenInvalidLogin() {
        String token = service.loginAndGenerateToken("wrong", "wrong");

        assertNull(token);
    }
}