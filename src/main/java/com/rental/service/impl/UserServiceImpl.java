package com.rental.service.impl;


import com.rental.service.UserService;
import com.rental.service.exception.UserExistsException;
import com.rental.persistence.model.entities.User;
import com.rental.persistence.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LogManager.getLogger(UserService.class);
    @Autowired
    private UserRepository userRepository;

    /**
     * <p>Method to create a new system user.</p>
     *
     * @param user user data to be created
     * @return USer the information of the user successfully created
     * @throws UserExistsException is the userName has already been taken
     */
    @Override
    public User createUser(User user) {

        User storedUser = userRepository.getUserByName(user.getName());
        if (storedUser != null) {
            LOGGER.error("The user already exists already exist.");
            throw new UserExistsException("Cannot create new user it already exist.");
        }
        LOGGER.debug("creating a new user with data: " + user.toString() + ".");
        return userRepository.createUser(user);

    }

    /**
     * <p>This method will search for specific users by his userName.</p>
     *
     * @param name id of the desired user.
     * @return User object with the user data.
     */
    @Override
    public User getUser(String name) {
        LOGGER.debug("Searching for user with id " + name + ".");
        return userRepository.getUserByName(name);
    }

}
