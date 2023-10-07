package com.shehabsalah.securespringstarter.user.services;

import com.shehabsalah.securespringstarter.user.dto.UserDTO;
import com.shehabsalah.securespringstarter.user.entities.User;
import org.springframework.stereotype.Service;

/**
 * The UserService interface defines the contract for managing user-related operations in the application.
 *
 * <p>
 * This interface provides a set of methods for interacting with user data, including retrieving user information
 * by email, getting the authenticated user's profile, checking if a user exists by email or mobile number,
 * saving user entities to the database, and counting the number of users in the system.
 *
 * <p>
 * Implementations of this interface handle user data and are responsible for performing CRUD (Create, Read, Update, Delete)
 * operations on user entities in the database. They also provide business logic for user-related functionalities.
 *
 * @see User
 * @see UserDTO
 * @author Shehab Salah
 * @version 1.0
 * @since 2023-10-06
 */
@Service
public interface UserService {

    /**
     * Get a user by their email. This method it takes the email and search for the user in the database if
     * the email is found then it returns the user object, otherwise it throws an exception.
     *
     * @param email the user's email
     * @return the user with the given email
     * @throws RuntimeException if the user is not found
     */
    User getUserByEmail(String email) throws RuntimeException;

    /**
     * This method is used to get the authenticated user data
     *
     * @return {@link UserDTO} the user data transfer object
     * @throws RuntimeException if the user is not found
     * */
    UserDTO getProfile() throws RuntimeException;

    /**
     * This method is used to check if the user exists by email or mobile number
     *
     * @param email the user email
     * @param mobile the user mobile number
     * @return true if the user exists, false otherwise
     * */
    boolean existsByEmailOrMobile(String email, String mobile);

    /**
     * This method is used to save a {@link User} object to the database
     *
     * @param user the user object to be saved
     * @return {@link User} the saved user object
     * @throws RuntimeException if the user is not saved
     */
    User save(User user) throws RuntimeException;

    /**
     * This method is used to get the number of users in the database
     *
     * @return {@link Long} the number of users
     * */
    Long count();
}
