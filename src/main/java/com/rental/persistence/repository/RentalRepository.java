package com.rental.persistence.repository;


import com.rental.persistence.model.entities.Rental;

import java.util.List;

/**
 * <p>Interface for accessing the rental repository.</p>
 *
 * @author Martín Díaz
 * @version 1.0
 */
public interface RentalRepository {
    /**
     * <p>Method to create a new rental.</p>
     *
     * @param rental object with the necessary information.
     * @return Rental object recently created.
     */
    Rental createRental(Rental rental);

    /**
     * <p>Method to retrieve all rentals created in the repository.</p>
     *
     * @return List of Rentals found the repository.
     */
    List<Rental> findAllRentals();

    /**
     * <p>Method for deleting a rental.</p>
     *
     * @param rentalId of the rental to be deleted
     * @return Rental object of the deleted rental.
     */
    Rental deleteRental(Long rentalId);
}
