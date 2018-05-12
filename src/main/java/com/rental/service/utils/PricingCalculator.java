package com.rental.service.utils;

import com.rental.persistence.model.entities.Rental;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Has the information and rates necessary to rent the bikes. the rates are:
 * price by hour, price by day and price by week. also the discount rate for the family promotion
 *
 * @author Martín Díaz
 * @version 1.0
 */
public class PricingCalculator {

    private static final Logger LOGGER = LogManager.getLogger(PricingCalculator.class);
    private static final int PRICE_HOUR = 5;
    private static final int PRICE_DAY = 20;
    private static final int PRICE_WEEK = 60;
    private static final int DAYS_IN_WEEK = 7;
    private static final int HOURS_IN_DAY = 24;
    private static final int MINIMUM_CUSTOMER_PROMO = 3;
    private static final int MAXIMUM_CUSTOMER_PROMO = 5;
    private static final double DISCOUNT = 0.7d;

    /**
     * Receives a customer rental request. And set the price to the request received
     * as a parameter. It was modeled this way in order to allow a better customization for
     * the customer, since he can rent bikes for different family members for different lengths.
     *
     * @param rental The amount to rentals the customer has requested.
     */
    public static void calculatePrice(Rental rental) {
        LOGGER.debug("Calculating price on rental for " +rental.getNumberOfBikes()+ " number of bikes and " + rental.getTime() + " total hours" );
        double price;
        double finalPrice;

        price = (getPricing(rental.getTime())*rental.getNumberOfBikes());

        finalPrice = rental.getNumberOfBikes() >= MINIMUM_CUSTOMER_PROMO && rental.getNumberOfBikes() <= MAXIMUM_CUSTOMER_PROMO ? price * DISCOUNT : price;

        LOGGER.debug("The final price is " + finalPrice);
        rental.setPrice(finalPrice);
    }

    /**
     * Private method that will return the total amount the customer has to pay before the discount.
     * this method will calculate the price id the customer has chosen an hour, a day, a week or any combination
     * of each option.
     *
     * @param time The total amount of hours the customer has the bike.
     * @return int The total amount for the order without a discount
     */
    private static int getPricing(int time) {
        LOGGER.debug("Calculating price on rental for total hours" );
        return (time / DAYS_IN_WEEK / HOURS_IN_DAY) * PRICE_WEEK + (time / HOURS_IN_DAY % DAYS_IN_WEEK) * PRICE_DAY + (time % HOURS_IN_DAY) * PRICE_HOUR;
    }
}
