package com.shehabsalah.securespringstarter.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * The JWTToken class is responsible for generating a JWT token for user authentication.
 *
 * <p>
 * It provides a method to generate a JWT token based on the user's email and password.
 * The generated token is used for authentication and authorization purposes.
 *
 * @see JWTUtils
 * @author Shehab Salah
 * @version 1.0
 * @since 2023-10-05
 */
@RequiredArgsConstructor
@Component
public class JWTToken {

    private final AuthenticationManager authenticationManager;
    private final JWTUtils jwtUtils;

    /**
     * Generate a JWT token for the user
     *
     * @param email the user's email
     * @param password the user's password
     * @return the generated JWT token
     */
    public String generate(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtUtils.generateJwtToken(authentication);
    }
}
