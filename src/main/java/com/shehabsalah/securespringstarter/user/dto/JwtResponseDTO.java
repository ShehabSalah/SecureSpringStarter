package com.shehabsalah.securespringstarter.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

/**
 * Data Transfer Object (DTO) class representing the response containing the JWT authentication details.
 *
 * <p>
 * This DTO class encapsulates the data returned in response to a successful JWT authentication. It includes the
 * authenticated user's information represented by a {@link UserDTO} object, the JWT token, and the type of token,
 * which is set to "Bearer" by default.
 *
 * @see UserDTO
 * @author Shehab Salah
 * @version 1.0
 * @since 2023-10-05
 */
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JwtResponseDTO {
    private UserDTO user;
    private String token;
    @Builder.Default
    private String type = "Bearer";
}
