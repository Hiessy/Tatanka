package com.rental.web.resources;

import com.rental.persistence.model.entities.Rental;
import org.springframework.hateoas.ResourceSupport;

import java.time.LocalDateTime;

/**
 * <p>Rental resource from and to the client.</p>
 *
 * @author Martín Díaz
 * @version 1.0
 */
public class RentalResource extends ResourceSupport {

    private Long rid;
    private Integer time;
    private Integer numberOfBikes;
    private Double price;
    private LocalDateTime rentalDateRequested;

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

    /**
     * <p>Mapping method to transform a rental resource to rental domain object.</p>
     *
     * @return Rental domain object.
     */
    public Rental toRental(){ return new Rental(time,numberOfBikes,price,rentalDateRequested);}

}
