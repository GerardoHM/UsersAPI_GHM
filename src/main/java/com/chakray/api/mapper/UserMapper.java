package com.chakray.api.mapper;

import com.chakray.api.dto.UserRequestDTO;
import com.chakray.api.dto.UserResponseDTO;
import com.chakray.api.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User toEntity(UserRequestDTO dto) {
        return User.builder()
                .email(dto.getEmail())
                .name(dto.getName())
                .phone(dto.getPhone())
                .password(dto.getPassword())
                .taxId(dto.getTaxId())
                .addresses(dto.getAddresses())
                .build();
    }

    public UserResponseDTO toDTO(User user) {
        return UserResponseDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .phone(user.getPhone())
                .taxId(user.getTaxId())
                .createdAt(user.getCreatedAt())
                .addresses(user.getAddresses())
                .build();
    }
}
