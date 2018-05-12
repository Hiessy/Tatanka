package com.rental.persistence.repository;


import com.rental.persistence.model.entities.User;

/**
 * <p>Interface class for accessing the user repository.</p>
 *
 * @author Martín Díaz
 * @version 1.0
 */
public interface UserRepository{
    /**
     * <p>Method for creating a new user in the repository.</p>
     * @param user with the necessary user information required.
     * @return User object newly created.
     */
    User createUser(User user);

    /**
     * <p>Method to query the repository for an existing user.</p>
     * @param name of the requested user.
     * @return User information found in the repository.
     */
    User getUserByName(String name);

    /**
     * <p>Method to retrieve a user by id</p>
     * @param id of the requested user.
     * @return User information found in the repository.
     */
    User getUserById(Long id);
}
