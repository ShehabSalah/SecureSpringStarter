package com.shehabsalah.securespringstarter.user.controllers;

import com.shehabsalah.securespringstarter.dto.ResponseDTO;
import com.shehabsalah.securespringstarter.user.dto.JwtResponseDTO;
import com.shehabsalah.securespringstarter.user.dto.LoginRequestDTO;
import com.shehabsalah.securespringstarter.user.dto.RegisterRequestDTO;
import com.shehabsalah.securespringstarter.user.services.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    /**
     * This endpoint used to register a user
     *
     * @param registerRequestDTO the register request data transfer object {@link RegisterRequestDTO}
     * @return the jwt response data transfer object {@link JwtResponseDTO}
     * */
    @PostMapping("/register")
    public ResponseEntity<ResponseDTO<JwtResponseDTO>> register(@Valid @RequestBody RegisterRequestDTO registerRequestDTO) {
        log.info("API ---> (/api/v1/auth/register) has been called.");
        log.info("Method Location: {}", this.getClass().getName() + ".register()");
        log.info("Request body: {}", registerRequestDTO);

        JwtResponseDTO jwtResponseDTO = authService.register(registerRequestDTO);

        ResponseDTO<JwtResponseDTO> responseDTO = ResponseDTO.<JwtResponseDTO>builder()
                .message("User has been registered successfully")
                .status(200)
                .success(true)
                .data(jwtResponseDTO)
                .build();

        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/users/" + jwtResponseDTO.getUser().getId()).toUriString());

        return ResponseEntity.created(uri).body(responseDTO);
    }

    /**
     * This endpoint used to log in a user
     *
     * @param loginRequestDTO the login request data transfer object {@link LoginRequestDTO}
     * @return the jwt response data transfer object {@link JwtResponseDTO}
     */
    @PostMapping("/login")
    public ResponseEntity<ResponseDTO<JwtResponseDTO>> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        log.info("API ---> (/api/v1/auth/login) has been called.");
        log.info("Method Location: {}", this.getClass().getName() + ".login()");
        log.info("Request body: {}", loginRequestDTO);

        JwtResponseDTO jwtResponseDTO = authService.login(loginRequestDTO);

        ResponseDTO<JwtResponseDTO> responseDTO = ResponseDTO.<JwtResponseDTO>builder()
                .message("User has been logged in successfully")
                .status(200)
                .success(true)
                .data(jwtResponseDTO)
                .build();

        return ResponseEntity.ok(responseDTO);
    }
}
