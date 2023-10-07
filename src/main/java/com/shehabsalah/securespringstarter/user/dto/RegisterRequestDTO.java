package com.shehabsalah.securespringstarter.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

/**
 * Data Transfer Object (DTO) class representing the user registration request information.
 *
 * <p>
 * This DTO class encapsulates the user registration request data sent by clients when registering a new user.
 * It contains fields for the user's first name, last name, email, mobile number, password, and confirm password.
 *
 * <p>
 * Various validation constraints are applied to the fields using the validation annotations, ensuring that
 * the required fields are not blank, the email is in a valid format, and the password and confirm password
 * meet the length requirements.
 *
 * @author Shehab Salah
 * @version 1.0
 * @since 2023-10-05
 */
@Data
@Builder
public class RegisterRequestDTO {
    @NotBlank(message = "First name is required")
    @Size(min = 3, max = 50, message = "First name must be between 3 and 50 characters")
    private String firstName;
    @NotBlank(message = "Last name is required")
    @Size(min = 3, max = 50, message = "Last name must be between 3 and 50 characters")
    private String lastName;
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be in a valid format")
    private String email;
    private String mobile;
    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 120, message = "Password must be between 6 and 120 characters")
    private String password;
    @NotBlank(message = "Confirm password is required")
    @Size(min = 6, max = 120, message = "Confirm password must be between 6 and 120 characters")
    private String confirmPassword;
}
