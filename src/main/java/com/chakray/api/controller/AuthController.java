package com.chakray.api.controller;

import com.chakray.api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AuthController {

    private final UserService service;

    public AuthController(UserService service) {
        this.service = service;
    }

    @Operation(summary = "Autenticación de usuario (retorna JWT)")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {

        String taxId = body.get("tax_id");
        String password = body.get("password");

        String token = service.loginAndGenerateToken(taxId, password);

        if (token == null) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }

        return ResponseEntity.ok(Map.of(
                "token", token
        ));
    }
}
