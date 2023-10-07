package com.shehabsalah.securespringstarter.user.controllers;

import com.shehabsalah.securespringstarter.dto.ResponseDTO;
import com.shehabsalah.securespringstarter.user.dto.UserDTO;
import com.shehabsalah.securespringstarter.user.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    /**
     * This endpoint used to get the authenticated user data
     *
     * @return the user data transfer object {@link UserDTO}
     * @throws RuntimeException if the user is not authenticated or if the user is not found
     * */
    @GetMapping("/profile")
    @PreAuthorize("hasAuthority('ROLE_USER') or hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ResponseDTO<UserDTO>> getProfile() throws RuntimeException {
        log.info("API ---> (/api/v1/user/profile) has been called.");
        log.info("Method Location: {}", this.getClass().getName() + ".getProfile()");

        UserDTO userDTO = userService.getProfile();

        ResponseDTO<UserDTO> responseDTO = ResponseDTO.<UserDTO>builder()
                .message("User has been retrieved successfully")
                .status(200)
                .success(true)
                .data(userDTO)
                .build();

        return ResponseEntity.ok(responseDTO);
    }
}
