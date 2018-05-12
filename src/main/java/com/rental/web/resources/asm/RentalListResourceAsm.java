package com.rental.web.resources.asm;

import com.rental.service.utils.RentalList;
import com.rental.web.controller.RentalController;
import com.rental.web.resources.RentalListResource;
import com.rental.web.resources.RentalResource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;

import java.util.List;

/**
 * <p>Resource assembler support for a Rental List object, that wraps the rental resource assembler</p>
 *
 * @author Martín Díaz
 * @version 1.0
 */
public class RentalListResourceAsm extends ResourceAssemblerSupport<RentalList, RentalListResource> {

    private final static Logger LOGGER = LogManager.getLogger(RentalListResourceAsm.class);

    /**
     * <p>Default constructor for the rental list resource assembler support.</p>
     *
     */
    public RentalListResourceAsm() {
        super(RentalController.class, RentalListResource.class);
    }

    /**
     * <p>Method to map the rental to rental resource adding the link to reference.</p>
     *
     * @param rentalList each with its full rental information.
     * @return RentalResource object with the necessary links attached.
     */
    @Override
    public RentalListResource toResource(RentalList rentalList) {
        LOGGER.debug("Mapping rental list object to rental list resource.");
        List<RentalResource> rentals = new RentalResourceAsm().toResources(rentalList.getRentals());
        RentalListResource rentalListResource = new RentalListResource(rentals);
        return rentalListResource;
    }
}
