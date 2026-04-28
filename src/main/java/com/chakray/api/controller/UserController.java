package com.chakray.api.controller;

import com.chakray.api.dto.UserRequestDTO;
import com.chakray.api.dto.UserResponseDTO;
import com.chakray.api.model.User;
import com.chakray.api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Tag(name = "Users", description = "Operaciones de usuarios")
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping
    public List<User> getUsers(
            @RequestParam(required = false) String sortedBy,
            @RequestParam(required = false) String filter) {

        return service.getUsers(sortedBy, filter);
    }

    @PostMapping
    public UserResponseDTO create(@Valid @RequestBody UserRequestDTO dto) {
        return service.create(dto);
    }

    @PatchMapping("/{id}")
    public User update(@PathVariable UUID id,
                       @RequestBody Map<String, Object> updates) {
        return service.update(id, updates);
    }

    @Operation(summary = "Eliminar usuario")
    @ApiResponse(responseCode = "200", description = "Usuario eliminado")
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

}
