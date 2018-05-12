package com.rental.web.resources;

import org.springframework.hateoas.ResourceSupport;

import java.util.List;

/**
 * <p>Immutable object for a list of rental resource objects.</p>
 *
 * @author Martín Díaz
 * @version 1.0
 */
public class RentalListResource extends ResourceSupport {

    private List<RentalResource> rentalResources;

    /**
     * <p>Parametrize constructor will require a list of rental resources.</p>
     *
     * @param rentalResources list information.
     */
    public RentalListResource(List<RentalResource> rentalResources) {
        this.rentalResources = rentalResources;
    }

    /**
     * <p>Default getter method to retrieve the rental resources.</p>
     *
     * @return List containing the rental resources.
     */
    public List<RentalResource> getRentalResources() {
        return rentalResources;
    }

}
