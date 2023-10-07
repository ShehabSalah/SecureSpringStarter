package com.shehabsalah.securespringstarter.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.shehabsalah.securespringstarter.user.enums.UserRole;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * A data transfer object (DTO) class representing a user.
 * The class provides a structured representation of user information for data exchange.
 * It includes fields such as ID, first name, last name, email, mobile, role, and activation status.
 *
 * <p>
 * It also includes a utility method {@code toViewDTO()} that nullifies sensitive fields for the user
 * view representation.
 *
 * @see UserRole
 * @author Shehab Salah
 * @version 1.0
 * @since 2023-10-05
 */
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String mobile;
    private String password;
    private UserRole role;
    private Boolean isActive;
    private Boolean isLocked;
    private Boolean isBlocked;
    private Boolean isDeleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * Nullifies sensitive fields for the user view representation.
     *
     * @return the user DTO object with sensitive fields nullified.
     */
    public UserDTO toViewDTO() {
        password = null;
        role = null;
        isActive = null;
        isLocked = null;
        isBlocked = null;
        isDeleted = null;
        createdAt = null;
        updatedAt = null;

        return this;
    }
}
