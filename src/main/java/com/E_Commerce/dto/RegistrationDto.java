package com.E_Commerce.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegistrationDto {
    @NotBlank(message = "First name is required")
    private String firstName;
    @NotBlank(message = "Last name is required")
    private String lastName;
    @NotBlank(message = "Email name is required")
    @Email(message = "Please enter a valid email address")
    private String email;
    @NotBlank(message = "Password name is required")
    @Size(min = 6, message = "Password must be at least 6 character long")
    private String password;
}