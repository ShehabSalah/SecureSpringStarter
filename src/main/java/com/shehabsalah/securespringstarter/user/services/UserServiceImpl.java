package com.shehabsalah.securespringstarter.user.services;

import com.shehabsalah.securespringstarter.security.UserPrincipal;
import com.shehabsalah.securespringstarter.user.dto.UserDTO;
import com.shehabsalah.securespringstarter.user.entities.User;
import com.shehabsalah.securespringstarter.user.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * UserServiceImpl class.
 *
 * <p>
 * This class implements the {@link UserService} interface and provides the implementation for user-related operations
 * in the application. It interacts with the {@link UserRepository} to perform database operations.
 *
 * @see UserService
 * @see UserRepository
 * @see User
 * @see UserDTO
 * @see UserPrincipal
 * @author Shehab Salah
 * @version 1.0
 * @since 2023-10-06
 */
@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getUserByEmail(String email) throws RuntimeException {
        return userRepository.findFirstByEmail(email)
                .orElseThrow(() -> {
                    log.error("User not found with email: " + email);
                    return new RuntimeException("User not found with email: " + email);
                });
    }

    @Override
    public UserDTO getProfile() throws RuntimeException {
        UserPrincipal userPrincipal =
                (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (userPrincipal == null || userPrincipal.getUser() == null) {
            log.error("User not found");
            throw new RuntimeException("User not found");
        }

        User currentUser = userRepository.findFirstByEmail(userPrincipal.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!currentUser.isActive() || currentUser.isLocked() || currentUser.isBlocked()) {
            throw new RuntimeException(
                    String.format(
                            "User with email: %s is not active or locked or blocked. Please contact the administrator.",
                            currentUser.getEmail()
                    )
            );
        } else if (currentUser.isDeleted()) {
            throw new RuntimeException(
                    String.format(
                            "User with email: %s is deleted. Please contact the administrator.",
                            currentUser.getEmail()
                    ));
        }

        return currentUser.toDTO().toViewDTO();
    }

    @Override
    public boolean existsByEmailOrMobile(String email, String mobile) {
        return userRepository.findFirstByEmailOrMobile(email, mobile).isPresent();
    }

    @Override
    public User save(User user) throws RuntimeException {
        return userRepository.saveAndFlush(user);
    }

    @Override
    public Long count() {
        return userRepository.count();
    }
}
