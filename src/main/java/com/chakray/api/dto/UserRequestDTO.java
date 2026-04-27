package com.chakray.api.dto;

import com.chakray.api.model.Address;
import com.chakray.api.validation.AndresFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.util.List;

@Data
public class UserRequestDTO {
    @NotBlank(message = "Email field is required.")
    @Email
    private String email;

    @NotBlank(message = "Name field is required.")
    private String name;

    @AndresFormat
    private String phone;

    @NotBlank(message = "Phone field is required.")
    private String password;

    @Pattern(regexp = "^[A-Z]{4}\\d{6}[A-Z0-9]{3}$")
    private String taxId;

    private List<Address> addresses;
}
