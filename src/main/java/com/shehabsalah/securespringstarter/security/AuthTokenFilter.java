package com.shehabsalah.securespringstarter.security;

import io.micrometer.common.lang.NonNull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;

/**
 * The AuthTokenFilter class is a filter that intercepts incoming HTTP requests and validates the JWT token
 * provided in the request's Authorization header. It extracts the token, verifies its validity, and sets the
 * corresponding user authentication in the SecurityContextHolder.
 *
 * <p>
 * This filter is responsible for authenticating requests by validating the JWT token and associating the
 * authenticated user with the request.
 *
 * @see JWTUtils
 * @see UserDetailsServiceImpl
 * @author Shehab Salah
 * @version 1.0
 * @since 2023-10-06
 */
@Slf4j
public class AuthTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    /**
     * Filters and processes the incoming HTTP request.
     * Validates the JWT token in the Authorization header, sets the user authentication, and passes the request
     * to the next filter in the chain.
     *
     * @param request     the HTTP request
     * @param response    the HTTP response
     * @param filterChain the filter chain for the request
     * @throws ServletException if an error occurs during the filter processing
     * @throws IOException      if an I/O error occurs during the filter processing
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        String jwt = parseJwt(request);
        if (jwt == null) {
            filterChain.doFilter(request, response);
            return;
        }

        String username = jwtUtils.extractUsername(jwt);
        if (username != null  && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (jwtUtils.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities());
                authentication.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                // log user authorities
                log.info("User authorities: {}", userDetails.getAuthorities());
                // check if the user not fully authenticated then authenticate the user
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }

    /**
     * Parses the JWT token from the Authorization header of the HTTP request.
     *
     * @param request the HTTP request
     * @return the JWT token if present in the Authorization header, null otherwise
     */
    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }

        return null;
    }
}
