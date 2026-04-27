package com.chakray.api.dto;

import com.chakray.api.model.Address;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class UserResponseDTO {
    private UUID id;
    private String email;
    private String name;
    private String phone;
    private String taxId;
    private String createdAt;
    private List<Address> addresses;
}
