package com.rental.service;


import com.rental.service.utils.RentalList;
import com.rental.persistence.model.entities.Rental;

/**
 *<p>Interface for accessing the rental service layer.</p>
 *
 * @author Martín
 * @version 1.0
 */
public interface RentalService {


    /**
     *<p>Method for getting the price on a rental.</p>
     *
     * @param rental object with the time and amount of bikes.
     * @return Rental object with the price.
     */
    Rental getPricing(Rental rental);

    /**
     *<p>Method for removing a booked rental.</p>
     *
     * @param rentalId for the rental.
     * @return Rental information that has been removed.
     */
    Rental removeRental(Long rentalId);

    /**
     *  <p>Method for retrieving all existing rentals.</p>
     *
     * @return RentalList containing all rentals created.
     */
    RentalList getAllRentals();
}
