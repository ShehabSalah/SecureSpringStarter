package com.shehabsalah.securespringstarter.user.repositories;

import com.shehabsalah.securespringstarter.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing user entities.
 *
 * <p>
 * This repository interface extends JpaRepository, allowing CRUD operations and queries to be
 * performed on the {@link User} entity in the database. The interface is responsible for defining
 * methods that enable interactions with the underlying database and provide easy access to user data.
 *
 * @see User
 * @author Shehab Salah
 * @version 1.0
 * @since 2023-10-05
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find a user by their email.
     *
     * @param email the user's email
     * @return the user with the given email
     */
    Optional<User> findFirstByEmail(String email);

    /**
     * Find a user by their email or mobile number.
     *
     * @param email the user's email
     * @param mobile the user's mobile number
     * @return the user with the given email or mobile number
     */
    Optional<User> findFirstByEmailOrMobile(String email, String mobile);
}
