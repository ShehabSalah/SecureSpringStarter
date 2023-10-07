package com.shehabsalah.securespringstarter.security;

import com.shehabsalah.securespringstarter.user.dto.UserDTO;
import com.shehabsalah.securespringstarter.user.entities.User;
import com.shehabsalah.securespringstarter.user.enums.UserRole;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The UserPrincipal class represents the authenticated user's principal details.
 * It implements the {@link UserDetails} interface provided by Spring Security.
 * It contains the user's information, such as username, password, and authorities (roles).
 *
 * <p>
 * The UserPrincipal class is used as the principal object for authentication and authorization purposes.
 * It is created based on the {@link UserDTO} object retrieved from the authentication module.
 * The UserPrincipal is used by Spring Security to perform user authentication and authorization checks.
 *
 * @see UserDTO
 * @see UserDetails
 * @author Shehab Salah
 * @version 1.0
 * @since 2023-10-05
 */
@Data
@Slf4j
public class UserPrincipal implements UserDetails {
    private transient User user;

    private Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(User user, Collection<? extends GrantedAuthority> authorities) {
        this.user = user;
        this.authorities = authorities;
    }

    /**
     * Builds a UserPrincipal object based on the provided UserDTO.
     * It converts the user's roles into GrantedAuthority objects for authorization purposes.
     *
     * @param user the UserDTO object representing the user's details
     * @return the UserPrincipal object
     */
    public static UserPrincipal build(User user) {
        List<UserRole> roles = new ArrayList<>();
        roles.add(user.getRole());

        List<GrantedAuthority> authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.toString()))
                .collect(Collectors.toList());

        return new UserPrincipal(
                user,
                authorities
        );
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !user.isBlocked() && !user.isLocked() && !user.isDeleted();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !user.isBlocked() && !user.isLocked() && user.isActive() && !user.isDeleted();
    }

    @Override
    public boolean isEnabled() {
        return user.isActive() && !user.isDeleted();
    }
}
