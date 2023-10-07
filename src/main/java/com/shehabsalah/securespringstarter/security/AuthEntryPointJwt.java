package com.shehabsalah.securespringstarter.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * The AuthEntryPointJwt class is an implementation of the Spring Security AuthenticationEntryPoint interface.
 * It handles unauthorized access requests and generates a JSON response with appropriate error details.
 *
 * <p>This component class is responsible for customizing the response for unauthorized requests.
 * When an unauthorized request is received, this class generates a JSON response with error details,
 * including the status code, error message, and request path.</p>
 *
 * @author Shehab Salah
 * @version 1.0
 * @since 2023-10-05
 */
@Component("delegatedAuthenticationEntryPoint")
@Slf4j
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

    /**
     * Handles unauthorized access requests and generates a JSON response with appropriate error details.
     *
     * @param request       the HTTP request
     * @param response      the HTTP response
     * @param authException the authentication exception
     * @throws IOException if an I/O error occurs during the filter processing
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException {

        log.error("Unauthorized error: {}", authException.getMessage());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        final Map<String, Object> body = new HashMap<>();
        body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        body.put("error", "Unauthorized");
        body.put("message", authException.getMessage());
        body.put("path", request.getServletPath());

        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(), body);
    }
}
