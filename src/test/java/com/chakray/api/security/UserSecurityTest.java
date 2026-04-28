package com.chakray.api.security;

import com.chakray.api.service.JwtService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private final JwtService jwtService = new JwtService("mysecretkeymysecretkeymysecretkey1234");

    @Test
    void shouldGenerateToken() {
        String token = jwtService.generateToken("AARR990101XXX");

        assertNotNull(token);
    }

    @Test
    void shouldExtractTaxId() {
        String token = jwtService.generateToken("AARR990101XXX");

        String taxId = jwtService.extractTaxId(token);

        assertEquals("AARR990101XXX", taxId);
    }
}