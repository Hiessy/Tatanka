package com.rental.persistence.model.entities;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * The Rental object represents one bike and thus is associated to one customer.
 * Contains a the total numberOfBikes of time the bike is needed in hours and the customerId.
 *
 * @author Martín Díaz
 * @version 1.0
 */
@Entity
@Table
public class Rental {

    @Id
    @GeneratedValue
    private Long id;
    @OneToOne
    private User user;

    private Integer time;

    private Integer numberOfBikes;

    private Double price;

    private LocalDateTime rentalDateRequested;

    public Rental(Integer time, Integer numberOfBikes, Double price, LocalDateTime rentalDateRequested) {
        this.time = time;
        this.numberOfBikes = numberOfBikes;
        this.price = price;
        this.rentalDateRequested = rentalDateRequested;
    }

    /**
     * <p>Default constructor required by the entity manage.</p>
     */
    public Rental() {
    }

    /**
     * <p>Default getter method for the rental id.</p>
     *
     * @return Long representing the rental id.
     */
    public Long getId() {
        return id;
    }

    /**
     * <p>Default setter method for the rental id.</p>
     *
     * @param id with Long value of the rental id.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * <p>Default getter method for the user requesting the rental.</p>
     *
     * @return User for the requested.
     */
    public User getUser() {
        return user;
    }

    /**
     * <p>Default setter method for the user requesting the rental.</p>
     *
     * @param user with the user data.
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * <p>Default getter method for the time.</p>
     *
     * @return Integer with the amount of hours.
     */
    public Integer getTime() {
        return time;
    }

    /**
     * <p>Default setter method for the time.</p>
     *
     * @param time the amount of hours.
     */
    public void setTime(Integer time) {
        this.time = time;
    }

    /**
     * <p>Default getter for the amount of bicycles needed.</p>
     *
     * @return Integer with the numbers of bicycles.
     */
    public Integer getNumberOfBikes() {
        return numberOfBikes;
    }

    /**
     * <p>Default getter for the amount of bicycles needed.</p>
     *
     * @param numberOfBikes the amount of bicycles request.
     */
    public void setNumberOfBikes(Integer numberOfBikes) {
        this.numberOfBikes = numberOfBikes;
    }

    /**
     * <p>Default getter for the price of the rental.</p>
     *
     * @return Double the price of the rental.
     */
    public Double getPrice() {
        return price;
    }

    /**
     * <p>Default setter for the price of the rental.</p>
     *
     * @param price with the price of the rental.
     */
    public void setPrice(Double price) {
        this.price = price;
    }

    /**
     * <p>Default getter with the date and time of the rental.</p>
     *
     * @return LocalDateTime that the rental is requested.
     */
    public LocalDateTime getRentalDateRequested() {
        return rentalDateRequested;
    }

    /**
     *<p>Default setter for the date and time of the rental.</p>
     *
     * @param rentalDateRequested the of the rental request.
     */
    public void setRentalDateRequested(LocalDateTime rentalDateRequested) {
        this.rentalDateRequested = rentalDateRequested;
    }


}