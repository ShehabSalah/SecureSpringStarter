package com.shehabsalah.securespringstarter.user.services;

import com.shehabsalah.securespringstarter.user.dto.JwtResponseDTO;
import com.shehabsalah.securespringstarter.user.dto.LoginRequestDTO;
import com.shehabsalah.securespringstarter.user.dto.RegisterRequestDTO;
import org.springframework.stereotype.Service;

/**
 * The AuthService interface defines the contract for handling all the authentication operations.
 * It provides a method for registration, login, verify, and reset operations.
 *
 * @see JwtResponseDTO
 * @see RegisterRequestDTO
 * @see LoginRequestDTO
 * @author Shehab Salah
 * @version 1.0
 * @since 2023-10-06
 */
@Service
public interface AuthService {

    /**
     * Register a new user
     *
     * @param registerRequestDTO the user information to register
     * @return the registered user information and the token {@link JwtResponseDTO}
     * @throws RuntimeException if the user already exists or there is an invalid field
     */
    JwtResponseDTO register(RegisterRequestDTO registerRequestDTO) throws RuntimeException;

    /**
     * This method is used to log in a user by email and password
     *
     * @param loginRequestDTO the user email and password
     * @return the logged-in user information and the token {@link JwtResponseDTO}
     * @throws RuntimeException if the email or password is invalid
     */
    JwtResponseDTO login(LoginRequestDTO loginRequestDTO) throws RuntimeException;
}
