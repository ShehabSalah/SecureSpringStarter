package com.shehabsalah.securespringstarter.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

/**
 * The WebSecurityConfig class is responsible for configuring the web security settings
 * for the application. It sets up authentication, authorization, and other security-related
 * configurations using Spring Security.
 *
 * <p>
 * The class is annotated with {@code @Configuration}, indicating that it is a configuration class.
 * It is also annotated with {@code @EnableWebSecurity} to enable Spring Security's web security support.
 * {@code @EnableGlobalMethodSecurity} is used to enable global method-level security for the application.
 *
 * <p>
 * The WebSecurityConfig class defines several beans and methods to customize the security behavior:
 *  <ul>
 *      <li>
 *          {@link #authenticationJwtTokenFilter()} Creates an instance of the AuthTokenFilter, which is responsible for
 *          filtering and validating JWT tokens in the request headers.
 *      </li>
 *      <li>
 *          {@link #authenticationProvider()}: Creates an instance of the DaoAuthenticationProvider, which is responsible for
 *          authenticating users based on the configured user details service and password encoder.
 *      </li>
 *      <li>
 *          {@link #authenticationManager(AuthenticationConfiguration)}: Creates an instance of the AuthenticationManager, which is responsible for
 *          authenticating the users during the authentication process.
 *      </li>
 *      <li>
 *          {@link #corsConfigurationSource()}: Configures the CORS (Cross-Origin Resource Sharing) settings to allow requests
 *          from any origin and with specific methods and headers.
 *      </li>
 *      <li>
 *          {@link #filterChain(HttpSecurity)}: Configures the security filter chain by defining the authentication entry point, session
 *          management, request authorization rules, and the order of the authentication filter in the filter chain.
 *      </li>
 *  </ul>
 *
 * <p>
 * The class also defines constants for whitelisted URLs that are accessible without authentication,
 * and URLs that require authentication for access.
 *
 * @see AuthTokenFilter
 * @see AuthenticationEntryPoint
 * @see UserDetailsServiceImpl
 * @see PasswordEncoder
 * @author Shehab Salah
 * @version 1.0
 * @since 2023-10-06
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    @Qualifier("delegatedAuthenticationEntryPoint")
    AuthenticationEntryPoint authEntryPoint;

    private static final String[] AUTH_WHITELIST = {
            "/",
            "/favicon.ico/**",
            "/swagger-ui.html/**",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/static/**",
            "/sw.js/**",
            "/api/v1/auth/**",
            "/api/v1/api-keys/**"
    };

    /**
     * Creates an instance of the AuthTokenFilter, which is responsible for filtering and validating JWT tokens
     * in the request headers.
     *
     * @return The created AuthTokenFilter instance.
     */
    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }


    /**
     * Creates an instance of the DaoAuthenticationProvider, which is responsible for authenticating users
     * based on the configured user details service and password encoder.
     *
     * @return The created DaoAuthenticationProvider instance.
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);

        return authProvider;
    }

    /**
     * Creates an instance of the AuthenticationManager, which is responsible for authenticating the users
     * during the authentication process.
     *
     * @param authConfig The AuthenticationConfiguration instance.
     * @return The created AuthenticationManager instance.
     * @throws Exception if an error occurs during the creation of the AuthenticationManager.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    /**
     * Configures the CORS (Cross-Origin Resource Sharing) settings to allow requests from any origin
     * and with specific methods and headers.
     *
     * @return The configured CorsConfigurationSource instance.
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT"));
        configuration.setAllowedHeaders(Arrays.asList("content-type", "authorization"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    /**
     * Configures the security filter chain by defining the authentication entry point, session management,
     * request authorization rules, and the order of the authentication filter in the filter chain.
     *
     * @param http The HttpSecurity instance.
     * @throws Exception if an error occurs during the configuration of the HttpSecurity.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(CsrfConfigurer::disable)
                .exceptionHandling(
                        httpSecurityExceptionHandlingConfigurer -> httpSecurityExceptionHandlingConfigurer
                                .authenticationEntryPoint(authEntryPoint)
                )
                .securityMatcher(AUTH_WHITELIST)
                .authorizeHttpRequests((request) -> request
                        .requestMatchers(antMatcher("/api/v1/**"))
                        .permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
