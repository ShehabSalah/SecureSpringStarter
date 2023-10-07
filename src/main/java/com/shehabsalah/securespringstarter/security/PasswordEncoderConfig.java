package com.shehabsalah.securespringstarter.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * The PasswordEncoderConfig class is a Spring configuration class that defines a bean for the BCryptPasswordEncoder.
 *
 * <p>
 * This bean can be used for encoding and verifying passwords in the application. The {@link BCryptPasswordEncoder}
 * uses the bcrypt hashing algorithm to securely hash passwords.
 *
 * @see BCryptPasswordEncoder
 * @author Shehab Salah
 * @version 1.0
 * @since 2023-10-05
 */
@Configuration
public class PasswordEncoderConfig {

    /**
     * Defines a bean for the BCryptPasswordEncoder.
     *
     * @return the BCryptPasswordEncoder bean
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
