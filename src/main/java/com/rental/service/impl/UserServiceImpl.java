package com.rental.service.impl;

import com.rental.persistence.model.entities.Rental;
import com.rental.persistence.model.entities.User;
import com.rental.persistence.repository.RentalRepository;
import com.rental.persistence.repository.UserRepository;
import com.rental.service.UserService;
import com.rental.service.exception.InvalidDateException;
import com.rental.service.exception.UserDoesNotExistsException;
import com.rental.service.exception.UserExistsException;
import com.rental.service.utils.PricingCalculator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = LogManager.getLogger(UserService.class);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RentalRepository rentalRespository;

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
        User foundUser = userRepository.getUserByName(name);
        return foundUser;
    }

    /**
     * <p>Method for getting the price on a rental. This method will calculate
     * the price for the rental request based on the business requirements.</p>
     *
     * @param userId of the user requesting the rental.
     * @param rental object with the data of the request.
     * @return Rental object that was persisted in the data base.
     * @throws InvalidDateException       if the requested date of the booking is before today.
     * @throws UserDoesNotExistsException if the user does not exist in the system.
     */
    @Override
    public Rental createRental(Long userId, Rental rental) {

        if (LocalDateTime.now().isAfter(rental.getRentalDateRequested())) {
            LOGGER.error("Invalid date has been entered");
            throw new InvalidDateException("Invalid date has been entered");
        }

        User storedUser = userRepository.getUserById(userId);
        if (storedUser == null) {
            LOGGER.error("Invalid user id for this request");
            throw new UserDoesNotExistsException("no user id was found for id: " + userId);
        }

        Double price = PricingCalculator.calculatePrice(rental.getTime(), rental.getNumberOfBikes());
        rental.setPrice(price);
        rental.setUser(storedUser);
        LOGGER.debug("Creating new rental");
        return rentalRespository.createRental(rental);
    }
}
