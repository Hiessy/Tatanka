package com.rental.service.impl;

import com.rental.persistence.model.entities.Rental;
import com.rental.persistence.repository.RentalRepository;
import com.rental.persistence.repository.UserRepository;
import com.rental.service.RentalService;
import com.rental.service.utils.PricingCalculator;
import com.rental.service.utils.RentalList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
     *  <p>Method for retrieving all existing rentals.</p>
     *
     * @return RentalList containing all rentals created.
     */
    @Override
    public RentalList getAllRentals() {
        LOGGER.debug("Retrieving all existing rentals");
        RentalList foundRentals = new RentalList(rentalRepository.findAllRentals());
        return foundRentals;
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
        Double price = PricingCalculator.calculatePrice(rental.getTime(),rental.getNumberOfBikes());
        rental.setPrice(price);
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

        Rental deletedRental = rentalRepository.deleteRental(rentalId);
        return deletedRental;
    }
}
