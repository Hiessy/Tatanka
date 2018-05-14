package com.rental.service.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * <p>Has the information and rates necessary to rent the bikes. the rates are:
 * price by hour, price by day and price by week. also the discount rate for the family promotion.
 * The pricing and promotions are set in the tatanka.properties file located in the resource folder</p>
 *
 * @author Martín Díaz
 * @version 1.0
 */
@Component
@PropertySource("classpath:tatanka.properties")
public class PricingCalculator {

    private static final Logger LOGGER = LogManager.getLogger(PricingCalculator.class);
    private static final Integer DAYS_IN_WEEK = 7;
    private static final Integer HOURS_IN_DAY = 24;

    @Value("${price.hour}")
    private Integer priceHour;
    @Value("${price.day}")
    private Integer priceDay;
    @Value("${price.week}")
    private Integer priceWeek;
    @Value("${minimum.customer.promo}")
    private Integer minimumCustomerPromo;
    @Value("${maximun.customer.promo}")
    private Integer maximumCustomerPromo;
    @Value("${discount}")
    private Double discount;

    /**
     * Receives a customer rental request. And set the price to the request received
     * as a parameter. It was modeled this way in order to allow a better customization for
     * the customer, since he can rent bikes for different family members for different lengths.
     *
     * @param time The amount to rentals the customer has requested.
     * @param numberOfBikes The total number of bikes the customer need.
     * @return double the price quoted for the customer.
     */
    public Double calculatePrice(Integer time, Integer numberOfBikes) {
        LOGGER.debug("Calculating price on rental for " + numberOfBikes + " number of bikes and " + time + " total hours" );

        Integer price = (getPricing(time)*numberOfBikes);
        Double finalPrice = numberOfBikes >= minimumCustomerPromo && numberOfBikes <= maximumCustomerPromo ? price * discount : price;

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
    private Integer getPricing(Integer time) {
        LOGGER.debug("Calculating price on rental for total hours" );
        return (time / DAYS_IN_WEEK / HOURS_IN_DAY) * priceWeek + (time / HOURS_IN_DAY % DAYS_IN_WEEK) * priceDay + (time % HOURS_IN_DAY) * priceHour;
    }

    /**
     * <p>Formatting the float value to the correct output. </p>
     *
     * @param number double value that would need to be fixed.
     * @return Double value with two decimals in case business logic changes.
     */
    private Double roundValue(Double number){
        BigDecimal result = new BigDecimal(number);
        result = result.setScale(2, BigDecimal.ROUND_HALF_UP);
        return result.doubleValue();
    }
}
