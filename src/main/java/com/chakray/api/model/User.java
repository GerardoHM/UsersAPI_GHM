package com.chakray.api.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private UUID id;

    @Email
    @NotBlank(message = "Email field is required.")
    private String email;

    @NotBlank(message = "Name field is required.")
    private String name;

    @NotBlank(message = "Phone field is required.")
    @Pattern(regexp = "^(\\+\\d{1,3})?\\d{10}$", message = "Invalid phone format")
    private String phone;

    @NotBlank(message = "Phone field is required.")
    private String password;

    @NotBlank(message = "Phone field is required.")
    @Pattern(regexp = "^[A-Z]{4}\\d{6}[A-Z0-9]{3}$", message = "Invalid RFC")
    private String taxId;

    private String createdAt;

    private List<Address> addresses;
}
