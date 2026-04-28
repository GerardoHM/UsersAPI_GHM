package com.chakray.api.dto;

import com.chakray.api.model.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
    private UUID id;
    private String email;
    private String name;
    private String phone;
    private String taxId;
    private String createdAt;
    private List<Address> addresses;
}
