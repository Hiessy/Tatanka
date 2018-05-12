package com.rental.web.resources.asm;


import com.rental.persistence.model.entities.User;
import com.rental.web.controller.UserController;
import com.rental.web.resources.UserResource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.hateoas.mvc.ResourceAssemblerSupport;


import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * <p>Resource Assembler Support following the HAETOS principle for working with REST clients
 * for the User requests from the clients</p>
 *
 * @author Martín Díaz
 * @version 1.0
 */
public class UserResourceAsm extends ResourceAssemblerSupport<User, UserResource> {

    private final static Logger LOGGER = LogManager.getLogger(UserResourceAsm.class);

    /**
     * <p>Default constructor for the rental resource assembler support.</p>
     */
    public UserResourceAsm() {
        super(UserController.class, UserResource.class);
    }

    /**
     * <p>Method to map the user to user resource adding the link to reference.</p>
     *
     * @param user User object to be mapped.
     * @return UserResource with the links to self reference.
     */
    @Override
    public UserResource toResource(User user){
        LOGGER.debug("Mapping user object to user resource.");
        UserResource userResource = new UserResource();
        userResource.setRid(user.getId());
        userResource.setName(user.getName());
        userResource.setPassword(user.getPassword());
        userResource.add(linkTo(methodOn(UserController.class).getUser(user.getName())).withSelfRel());
        return userResource;
    }
}
