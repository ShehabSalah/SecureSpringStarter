package com.shehabsalah.securespringstarter.user.config;

import com.shehabsalah.securespringstarter.user.entities.User;
import com.shehabsalah.securespringstarter.user.enums.UserRole;
import com.shehabsalah.securespringstarter.user.services.UserService;
import com.shehabsalah.securespringstarter.utils.ValidateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuration class for initializing the admin user when the Spring application starts.
 *
 * <p>
 * This class is responsible for checking if the users table is empty and adding the admin user with the specified
 * email and password if the table is empty. It provides an {@code initAdminUser()} method that performs the
 * initialization logic.
 *
 * <p>
 * The admin user is added only if the users table is empty, ensuring that the initialization is performed
 * only once during application startup.
 *
 * <p>
 * The class uses the {@link UserService} to interact with the user repository for user-related operations.
 *
 * @see UserService
 * @see User
 * @see PasswordEncoder
 * @see ValidateUtil
 * @see UserRole
 * @author Shehab Salah
 * @version 1.0
 * @since 2023-10-07
 */
@Configuration
@Slf4j
public class InitUser {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${app.admin.email}")
    private String adminEmail;
    @Value("${app.admin.password}")
    private String adminPassword;

    /**
     * Initializes the admin user if the users table is empty.
     *
     * <p>
     * This method is responsible for adding the admin user with the specified email and password to the users table.
     *
     * <p>
     * It performs the following steps:
     * <ol>
     *     <li>Checks if the users table is empty.</li>
     *     <li>Validates the admin email and password.</li>
     *     <li>Creates an admin user entity and saves it to the database using the {@link UserService}.</li>
     * </ol>
     *
     * <p>
     * The admin user is added only if the users table is empty, ensuring that the initialization is performed
     * only once during application startup.
     *
     * @throws RuntimeException if there are validation errors or if the admin user cannot be added.
     */
    @Bean
    public void initAdminUser() {
        log.info("Initializing admin user");

        // check if the users table is empty or not, if it's not empty then don't initialize the admin user
        if (userService.count() > 0) {
            return;
        }

        log.info("User table is empty, adding the admin user with email: {}, and password: {}", adminEmail, adminPassword);

        // validate the user email and mobile number
        ValidateUtil validateUtil = new ValidateUtil();

        // check if the user provided a valid email address
        if (!validateUtil.checkEmailValidation(adminEmail)) {
            throw new RuntimeException("Invalid email address! Please provide proper email address.");
        }

        // check if user password between 6 and 120 characters
        if (adminPassword.length() < 6 || adminPassword.length() > 120) {
            throw new RuntimeException("Invalid password! Please provide a password between 6 and 120 characters.");
        }

        User user = User.builder()
                .firstName("Admin")
                .lastName("User")
                .email(adminEmail)
                .password(passwordEncoder.encode(adminPassword))
                .role(UserRole.ROLE_ADMIN)
                .build();

        userService.save(user);
        log.info("Admin user has been added successfully");
    }
}
