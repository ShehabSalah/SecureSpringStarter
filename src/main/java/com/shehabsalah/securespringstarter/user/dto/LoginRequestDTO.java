package com.shehabsalah.securespringstarter.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Data Transfer Object (DTO) class representing the login request information.
 *
 * <p>
 * This DTO class encapsulates the login request data sent by clients to authenticate users. It contains fields
 * for the user's email, and password.
 *
 * @author Shehab Salah
 * @version 1.0
 * @since 2023-10-05
 */
@Data
public class LoginRequestDTO {
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be in a valid format")
    private String email;
    @NotBlank(message = "Password is required")
    private String password;
}
