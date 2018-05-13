package com.rental.web.controller;

import com.rental.persistence.model.entities.Rental;
import com.rental.service.RentalService;
import com.rental.service.utils.RentalList;
import com.rental.web.exception.NotFoundException;
import com.rental.web.resources.RentalListResource;
import com.rental.web.resources.RentalResource;
import com.rental.web.resources.asm.RentalListResourceAsm;
import com.rental.web.resources.asm.RentalResourceAsm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


/**
 * <p>Controller class to handle rental request. A rental object will be passed by various methods
 * with the option to have unregistered users get a quote for a rental.</p>
 *
 * @author Martín Díaz
 * @version 1.0
 */

@RestController
@RequestMapping("/v1/rental")
public class RentalController {

    private final static Logger LOGGER = LogManager.getLogger(RentalController.class);
    private RentalService rentalService;

    @Autowired
    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    /**
     * <p>GET method to retrieve all existing rental requests.</p>
     *
     * @return List of RentalResources with all existing rentals with the appropriate HTTP code. If no rental are
     *         created the response code will be NOT_FOUND.
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<RentalListResource> getAllRentals() {
        LOGGER.info("Getting all rentals");
        RentalList rentalList = rentalService.getAllRentals();
        if (rentalList.getRentals().isEmpty()) {
            LOGGER.error("No rentals where found");
            throw new NotFoundException("No rental have been created");
        }
        LOGGER.info("Rentals where found successfully");
        RentalListResource rentalListResource = new RentalListResourceAsm().toResource(rentalList);
        return new ResponseEntity<>(rentalListResource, HttpStatus.OK);
    }

    /**
     * <p>PUT method for the quote on renting bicycles for a specific time and for a specific amount of bikes.</p>
     *
     * @param rentalResource That will have the amount of hours and the amount of bicycles requested.
     * @return RentalResource with the appropriate HTTP code and the price for the requested quote.
     */
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<RentalResource> getRentalPricing(@RequestBody @Validated RentalResource rentalResource) {
        LOGGER.info("Get price for rental");
        Rental rentalWithQuote = rentalService.getPricing(rentalResource.toRental());
        RentalResource rentalResourceWithQuote = new RentalResourceAsm().toResource(rentalWithQuote);
        return new ResponseEntity<>(rentalResourceWithQuote, HttpStatus.OK);
    }

    /**
     * <p>DELETE method to remove a previously booked rental.</p>
     *
     * @param rentalId for the rental option that the customer wants to cancel.
     * @return RentalResource for the deleted rental option, if none was found the http status will be NOT_FOUND.
     *         Otherwise the appropriate HTTP code will be returned.
     * @throws NotFoundException if the request rental does not exist.
     */
    @RequestMapping(value = "{rentalId}", method = RequestMethod.DELETE)
    public ResponseEntity<RentalResource> removeRental(@PathVariable Long rentalId) {
        LOGGER.info("Deleting rental with requested id");
        Rental deletedRental = rentalService.removeRental(rentalId);
        if (deletedRental == null) {
            LOGGER.error("No rental exists with id " + rentalId);
            throw new NotFoundException("No rental exists with requested id");
        }
        LOGGER.info("Rentals deleted successfully");
        RentalResource rentalResource = new RentalResourceAsm().toResource(deletedRental);
        return new ResponseEntity<>(rentalResource, HttpStatus.OK);
    }
}
