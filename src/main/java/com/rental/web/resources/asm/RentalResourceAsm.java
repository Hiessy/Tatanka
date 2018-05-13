package com.rental.web.resources.asm;


import com.rental.persistence.model.entities.Rental;
import com.rental.web.controller.RentalController;
import com.rental.web.controller.UserController;
import com.rental.web.resources.RentalResource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * <p>Resource Assembler Support following the HAETOS principle for working with REST clients
 * for the rental requests</p>
 *
 * @author Martín Díaz
 * @version 1.0
 */
public class RentalResourceAsm extends ResourceAssemblerSupport<Rental, RentalResource> {

    private final static Logger LOGGER = LogManager.getLogger(RentalResourceAsm.class);

    /**
     * <p>Default constructor for the rental resource assembler support.</p>
     */
    public RentalResourceAsm() {
        super(RentalController.class, RentalResource.class);
    }

    /**
     * <p>Method to map the rental to rental resource adding the link to reference.</p>
     *
     * @param rental User object to be mapped.
     * @return RentalResource with the links to self reference.
     */
    @Override
    public RentalResource toResource(Rental rental) {
        LOGGER.debug("Mapping rental object to rental resource.");
        RentalResource rentalResource = new RentalResource();
        rentalResource.setRid(rental.getId());
        rentalResource.setNumberOfBikes(rental.getNumberOfBikes());
        rentalResource.setPrice(rental.getPrice());
        rentalResource.setRentalDateRequested(rental.getRentalDateRequested());
        rentalResource.setTime(rental.getTime());
        if (rental.getUser() != null)
            rentalResource.add(linkTo(methodOn(UserController.class).getUser(rental.getUser().getName())).withRel("user"));
        return rentalResource;
    }

}
