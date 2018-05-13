package com.rental.service.utils;

import com.rental.persistence.model.entities.Rental;

import java.util.List;

/**
 * Wrapper rental list for the list of rentals.
 *
 * @author Martín Díaz
 * @version 1.0
 */
public class RentalList {

    List<Rental> rentals;

    public RentalList(List<Rental> rentals) {
        this.rentals = rentals;
    }

    public List<Rental> getRentals() {
        return rentals;
    }

}
