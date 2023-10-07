package com.shehabsalah.securespringstarter.user.services;

import com.shehabsalah.securespringstarter.security.JWTToken;
import com.shehabsalah.securespringstarter.user.dto.JwtResponseDTO;
import com.shehabsalah.securespringstarter.user.dto.LoginRequestDTO;
import com.shehabsalah.securespringstarter.user.dto.RegisterRequestDTO;
import com.shehabsalah.securespringstarter.user.entities.User;
import com.shehabsalah.securespringstarter.user.enums.UserRole;
import com.shehabsalah.securespringstarter.utils.ValidateUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Implementation class for AuthService interface.
 *
 * <p>
 * This class provides the implementation of the methods defined in the AuthService interface. It is responsible for
 * handling user authentication, registration, password reset, and related operations.
 *
 * @see AuthService
 * @see UserService
 * @see PasswordEncoder
 * @see JWTToken
 * @author Shehab Salah
 * @version 1.0
 * @since 2023-10-06
 */
@Service
@AllArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JWTToken jwtToken;

    @Override
    public JwtResponseDTO register(RegisterRequestDTO registerRequestDTO) throws RuntimeException {
        log.info("Registering user with email: {} and mobile: {}", registerRequestDTO.getEmail(), registerRequestDTO.getMobile());

        // validate the user email and mobile number
        ValidateUtil validateUtil = new ValidateUtil();

        // check if the user provided a valid email address
        if (!validateUtil.checkEmailValidation(registerRequestDTO.getEmail())) {
            throw new RuntimeException("Invalid email address! Please provide proper email address.");
        }

        // check if the user provided a valid mobile number
        if (registerRequestDTO.getMobile() != null) {
            if (!validateUtil.checkMobileNumberValidation(registerRequestDTO.getMobile())) {
                throw new RuntimeException("Invalid mobile number! Please provide proper mobile number.");
            }
        }

        // check if the user already exists
        if (userService.existsByEmailOrMobile(registerRequestDTO.getEmail(), registerRequestDTO.getMobile())) {
            throw new RuntimeException(String.format("User with email: %s or mobile: %s already exists.", registerRequestDTO.getEmail(), registerRequestDTO.getMobile()));
        }

        // check if password and confirm password are the same
        if (!registerRequestDTO.getPassword().equals(registerRequestDTO.getConfirmPassword())) {
            throw new RuntimeException("Password and confirm password are not the same.");
        }

        User user = User.builder()
                .firstName(registerRequestDTO.getFirstName())
                .lastName(registerRequestDTO.getLastName())
                .email(registerRequestDTO.getEmail())
                .mobile(registerRequestDTO.getMobile())
                .password(passwordEncoder.encode(registerRequestDTO.getPassword()))
                .role(UserRole.ROLE_USER)
                .build();

        user = userService.save(user);

        // Generate JWT token
        String jwt = jwtToken.generate(registerRequestDTO.getEmail(), registerRequestDTO.getPassword());
        log.debug("User JWT: {}", jwt);

        return JwtResponseDTO.builder()
                .token(jwt)
                .type("Bearer")
                .user(user.toDTO().toViewDTO())
                .build();
    }

    @Override
    public JwtResponseDTO login(LoginRequestDTO loginRequestDTO) throws RuntimeException {
        // get the user info
        User user = userService.getUserByEmail(loginRequestDTO.getEmail());

        // Generate JWT token
        String jwt = jwtToken.generate(loginRequestDTO.getEmail(), loginRequestDTO.getPassword());
        log.debug("User JWT: {}", jwt);

        return JwtResponseDTO.builder()
                .token(jwt)
                .type("Bearer")
                .user(user.toDTO().toViewDTO())
                .build();
    }
}
