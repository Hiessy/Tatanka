package com.rental.web.controller;


import com.rental.service.UserService;
import com.rental.service.exception.UserExistsException;
import com.rental.persistence.model.entities.User;
import com.rental.web.exception.ConflictException;
import com.rental.web.exception.NotFoundException;
import com.rental.web.resources.UserResource;
import com.rental.web.resources.asm.UserResourceAsm;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

/**
 * <p>Controller class for handling user requests for creating a new user and
 * retrieving existing user information.</p>
 *
 * @author Martín Díaz
 * @version 1.0
 */
@RestController
@RequestMapping("/v1/user")
public class UserController {

    private final static Logger LOGGER = LogManager.getLogger(UserController.class);
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * <p>POST method to create a new user.</p>
     *
     * @param sentUser that need to be persisted in database.
     * @return UserResource with all the information of the newly created user and the appropriate HTTP status code.
     * @throws UserExistsException is the user has already been created.
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<UserResource> createUser(@RequestBody UserResource sentUser) {
        LOGGER.info("Creating new user");
        try {
            User newUser = sentUser.toUser();
            User createdUser = userService.createUser(newUser);
            LOGGER.info("User created successfully");
            UserResource userResource = new UserResourceAsm().toResource(createdUser);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create(userResource.getLink("self").getHref()));
            return new ResponseEntity<>(userResource, headers, HttpStatus.CREATED);
        } catch (UserExistsException e) {
            LOGGER.error("Error creating new user, the user is already taken");
            throw new ConflictException(e);
        }
    }

    /**
     * <p>GET method to retrieve a specific user user the Username created originally.</p>
     *
     * @param name representing the user name that was created originally with the user.
     * @return UserResource wrapped in the response entity with the appropriate HTTP status code.
     * @throws NotFoundException if the request user was not found.
     */
    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public ResponseEntity<UserResource> getUser(@PathVariable String name) {
        LOGGER.info("Retrieving information for user");
        User retrievedUser = userService.getUser(name);
        if (retrievedUser != null) {
            LOGGER.info("User was found successfully");
            UserResource userResource = new UserResourceAsm().toResource(retrievedUser);
            return new ResponseEntity<>(userResource, HttpStatus.OK);
        } else {
            LOGGER.error("No user exists with that name");
            throw new NotFoundException("No user exists with that name");
        }
    }
}
