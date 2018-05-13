package com.rental.service.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;

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
     * @param time The amount to rentals the customer has requested.
     * @param numberOfBikes The total number of bikes the customer need.
     * @return double the price quoted for the customer.
     */
    public static Double calculatePrice(Integer time, Integer numberOfBikes) {
        LOGGER.debug("Calculating price on rental for " + numberOfBikes + " number of bikes and " + time + " total hours" );

        Integer price = (getPricing(time)*numberOfBikes);
        Double finalPrice = numberOfBikes >= MINIMUM_CUSTOMER_PROMO && numberOfBikes <= MAXIMUM_CUSTOMER_PROMO ? price * DISCOUNT : price;

        LOGGER.debug("The final price is " + finalPrice);

        return roundValue(finalPrice);

    }

    /**
     * <p>Private method that will return the total amount the customer has to pay before the discount.
     * this method will calculate the price id the customer has chosen an hour, a day, a week or any combination
     * of each option.</p>
     *
     * @param time The total amount of hours the customer has the bike.
     * @return int The total amount for the order without a discount
     */
    private static Integer getPricing(Integer time) {
        LOGGER.debug("Calculating price on rental for total hours" );
        return (time / DAYS_IN_WEEK / HOURS_IN_DAY) * PRICE_WEEK + (time / HOURS_IN_DAY % DAYS_IN_WEEK) * PRICE_DAY + (time % HOURS_IN_DAY) * PRICE_HOUR;
    }

    /**
     * <p>Formatting the float value to the correct output. </p>
     *
     * @param number double value that would need to be fixed.
     * @return Double value with two decimals in case business logic changes.
     */
    private static Double roundValue(Double number){
        BigDecimal result = new BigDecimal(number);
        result = result.setScale(2, BigDecimal.ROUND_HALF_UP);
        return result.doubleValue();
    }
}
