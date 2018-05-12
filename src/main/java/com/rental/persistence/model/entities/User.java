package com.rental.persistence.model.entities;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * <p>Domain object using Entity Manager and code first principle</p>
 *
 * @author Martín Díaz
 * @version 1.0
 */
@Entity
@Table
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    @Size(max=25)
    @Column(unique=true)
    private String name;

    @NotBlank
    @Size(max=25)
    private String password;

    public User(@NotBlank @Size(max = 25) String name, @NotBlank @Size(max = 25) String password) {
         this.name = name;
        this.password = password;
    }

    /**
     * <p>Default constructor required by the entity manage.</p>
     */
    public User() {
    }

    /**
     * <p>Default getter method for the user id.</p>
     *
     * @return Long representing the user id.
     */
    public Long getId() {
        return id;
    }

    /**
     * <p>Default setter method for the user id.</p>
     *
     * @param id with Long value of the customer id.
     */
    public void setId(Long id) {
        this.id = id;
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
    public String getPassword() {
        return password;
    }
    /**
     * <p>Default setter method for the password.</p>
     *
     * @param password with the password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
