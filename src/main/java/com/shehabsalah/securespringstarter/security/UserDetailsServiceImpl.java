package com.shehabsalah.securespringstarter.security;

import com.shehabsalah.securespringstarter.user.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * The UserDetailsServiceImpl class is a Spring service class that implements the UserDetailsService interface.
 *
 * <p>
 * This class is responsible for loading the user's data from the database and returning a {@link UserPrincipal} object.
 *
 * @see UserPrincipal
 * @see UserService
 * @author Shehab Salah
 * @version 1.0
 * @since 2023-10-06
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserService userService;

    @Override
    public UserPrincipal loadUserByUsername(String username) throws UsernameNotFoundException {
        // check if the username exists in the user table
        return UserPrincipal.build(userService.getUserByEmail(username));
    }
}
