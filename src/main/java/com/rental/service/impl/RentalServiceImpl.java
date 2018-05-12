package com.rental.service.impl;

import com.rental.service.RentalService;
import com.rental.service.exception.InvalidDateException;
import com.rental.service.exception.UserDoesNotExistsException;
import com.rental.service.utils.PricingCalculator;
import com.rental.service.utils.RentalList;
import com.rental.persistence.model.entities.Rental;
import com.rental.persistence.model.entities.User;
import com.rental.persistence.repository.RentalRepository;
import com.rental.persistence.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 *<p>Implementation of the rental service interface.</p>
 *
 * @author Martín
 * @version 1.0
 */
@Service
public class RentalServiceImpl implements RentalService {

    private static final Logger LOGGER = LogManager.getLogger(RentalService.class);

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * <p>Method for getting the price on a rental. This method will calculate
     *  the price for the rental request based on the business requirements.</p>
     *
     * @param userId of the user requesting the rental.
     * @param rental object with the data of the request.
     * @return Rental object that was persisted in the data base.
     * @throws InvalidDateException if the requested date of the booking is before today.
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

        PricingCalculator.calculatePrice(rental);
        rental.setUser(storedUser);
        LOGGER.debug("Creating new rental");
        return rentalRepository.createRental(rental);
    }

    /**
     *  <p>Method for retrieving all existing rentals.</p>
     *
     * @return RentalList containing all rentals created.
     */
    @Override
    public RentalList getAllRentals() {
        LOGGER.debug("Retrieving all existing rentals");
        return new RentalList(rentalRepository.findAllRentals());
    }

    /**
     *<p>Method for getting the price on a rental.</p>
     *
     * @param rental object with the time and amount of bikes.
     * @return Rental object with the price.
     */
    @Override
    public Rental getPricing(Rental rental) {
        LOGGER.debug("Calculating price");
        PricingCalculator.calculatePrice(rental);
        return rental;
    }

    /**
     *<p>Method for removing a booked rental.</p>
     *
     * @param rentalId for the rental.
     * @return Rental information that has been removed.
     */
    @Override
    public Rental removeRental(Long rentalId) {
        LOGGER.debug("Deleting rental with id" + rentalId);
        return rentalRepository.deleteRental(rentalId);
    }
}
