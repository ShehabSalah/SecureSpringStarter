package com.shehabsalah.securespringstarter.user.entities;

import com.shehabsalah.securespringstarter.base.BaseEntityAudit;
import com.shehabsalah.securespringstarter.user.dto.UserDTO;
import com.shehabsalah.securespringstarter.user.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name="users")
public class User extends BaseEntityAudit {

    @Column(nullable = false)
    @NotBlank(message = "First name is required")
    @Size(min = 3, max = 50, message = "First name must be between 3 and 50 characters")
    private String firstName;
    @Column(nullable = false)
    @NotBlank(message = "Last name is required")
    @Size(min = 3, max = 50, message = "Last name must be between 3 and 50 characters")
    private String lastName;
    @Email
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String mobile;
    @Column(nullable = false)
    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 120, message = "Password must be between 6 and 120 characters")
    private String password;
    @Column
    @Enumerated(EnumType.STRING)
    private UserRole role;
    @Column(name = "is_active", nullable = false, columnDefinition = "boolean default true")
    @Builder.Default
    private boolean active = true;
    @Column(name = "is_locked", nullable = false, columnDefinition = "boolean default false")
    @Builder.Default
    private boolean locked = false;
    @Column(name = "is_blocked", nullable = false, columnDefinition = "boolean default false")
    @Builder.Default
    private boolean blocked = false;
    @Column(name = "is_deleted", nullable = false, columnDefinition = "boolean default false")
    @Builder.Default
    private boolean deleted = false;

    /**
     * A utility method that converts the user entity to a user data transfer object (DTO).
     *
     * @return a user data transfer object (DTO) representing the user entity.
     **/
    public UserDTO toDTO() {
        return UserDTO.builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .mobile(mobile)
                .role(role)
                .isActive(active)
                .isLocked(locked)
                .isBlocked(blocked)
                .isDeleted(deleted)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }
}
