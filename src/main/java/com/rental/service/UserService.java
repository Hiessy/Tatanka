package com.rental.service;

import com.rental.persistence.model.entities.User;

/**
 * <p>Interface for accessing the user service layer.</p>
 *
 * @author Martín Díaz
 * @version 1.0
 */
public interface UserService {

    /**
     * <p>Create user method from the service layer.</p>
     *
     * @param user with the user data.
     * @return User object if successfully persisted.
     */
    User createUser(User user);

    /**
     * <p>Method to retrieve user by his name.</p>
     *
     * @param name of the user.
     * @return User object with all the data.
     */
    User getUser(String name);

}
