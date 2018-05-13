package com.rental.web.resources;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rental.persistence.model.entities.User;
import org.springframework.hateoas.ResourceSupport;

import javax.validation.constraints.NotBlank;

/**
 * <p>User resource from and to the client.</p>
 * @author Martín Díaz
 * @version 1.0
 */
public class UserResource extends ResourceSupport {

    private Long rid;
    @NotBlank
    private String name;
    @NotBlank
    private String password;

    /**
     * <p>Default getter method for the resource id.</p>
     *
     * @return Long representing the resource id.
     */
    public Long getRid() {
        return rid;
    }

    /**
     * <p>Default setter method for the resource id.</p>
     *
     * @param rid with Long value of the resource id.
     */
    public void setRid(Long rid) {
        this.rid = rid;
    }

    /**
     * <p>Default getter method for the user name.</p>
     *
     * @return String with the name of user.
     */
    public String getName() {
        return name;
    }

    /**
     * <p>Default setter method for the user name.</p>
     *
     * @param name of the user.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * <p>Default getter method for the password.</p>
     *
     * @return String with the user password
     */
    @JsonIgnore
    public String getPassword() {
        return password;
    }
    /**
     * <p>Default setter method for the password.</p>
     *
     * @param password with the password
     */

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * <p>Mapping method to transform a user resource to user domain object.</p>
     *
     * @return User domain object.
     */
    public User toUser(){return new User(this.name,this.password);}

}
