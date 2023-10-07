package com.shehabsalah.securespringstarter.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

/**
 * The JWTUtils class provides utility methods for handling JSON Web Tokens (JWT) in the authentication process.
 *
 * <p>
 * This component class is responsible for generating and validating JWT tokens based on the provided authentication.
 * It uses the JWT secret and expiration duration from the application configuration.
 *
 * @author Shehab Salah
 * @version 1.0
 * @since 2023-10-05
 */
@Component
@Slf4j
public class JWTUtils {

    @Value("${app.jwt.expirationMs}")
    private String jwtExpirationMs;
    @Value("${app.jwt.secret}")
    private String jwtSecret;

    /**
     * Generates a JWT token based on the provided authentication.
     *
     * @param authentication The authentication object containing the principal and credentials.
     * @return The generated JWT token as a string.
     */
    public String generateJwtToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .claim("roles", userPrincipal.getAuthorities()) // Extract roles from the UserPrincipal and include them in the token
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + Integer.parseInt(jwtExpirationMs)))
                .signWith(getSignInKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    /**
     * Extracts the username from a given JWT token.
     *
     * @param authToken The JWT token from which to extract the username.
     * @return The username extracted from the JWT token.
     */
    public String extractUsername(String authToken) {
        return extractClaim(authToken, Claims::getSubject);
    }

    /**
     * Validates the integrity and expiration of a JWT token.
     *
     * @param authToken The JWT token to validate.
     * @return {@code true} if the token is valid, {@code false} otherwise.
     */
    public boolean validateJwtToken(String authToken) {
        try {
            String username = extractClaim(authToken, Claims::getSubject);
            if (username == null) {
                log.error("Invalid JWT token: Invalid token subject");
                return false;
            }

            log.info("Claims Subject: {}", username);
            return true;
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        }

        return false;
    }

    /**
     * Validates the integrity and expiration of a JWT token.
     *
     * @param token The JWT token to validate.
     * @param userDetails The user details object containing the user's information.
     * @return {@code true} if the token is valid, {@code false} otherwise.
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /**
     * Checks if a JWT token is expired.
     *
     * @param token The JWT token to check.
     * @return {@code true} if the token is expired, {@code false} otherwise.
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extracts the expiration date from a given JWT token.
     *
     * @param token The JWT token from which to extract the expiration date.
     * @return The expiration date extracted from the JWT token.
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extracts a claim from a given JWT token.
     *
     * @param token The JWT token from which to extract the claim.
     * @param claimsResolver The claims resolver function.
     * @param <T> The type of the claim to extract.
     * @return The claim extracted from the JWT token.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extracts all claims from a given JWT token.
     *
     * @param token The JWT token from which to extract the claims.
     * @return The claims extracted from the JWT token.
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Generates a signing key based on the JWT secret.
     *
     * @return The generated signing key.
     */
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
