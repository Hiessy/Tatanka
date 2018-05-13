package com.rental.service;

import com.rental.service.utils.PricingCalculator;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * <p>Testing the business logic of the rately fees by hour, day or week and their promotions.</p>
 *
 * @author Martin Diaz
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class PricingCalculatorTest extends TestCase {

    @Test
    public void testHourlyRateForOneCustomer() {
        assertEquals(5.0,PricingCalculator.calculatePrice(1,1));
        assertEquals(75.0,PricingCalculator.calculatePrice(15,1));
        assertEquals(115.0,PricingCalculator.calculatePrice(23,1));
    }

    @Test
    public void testHourlyRateForTwoCustomer(){
        assertEquals(10.0,PricingCalculator.calculatePrice(1,2));
        assertEquals(150.0,PricingCalculator.calculatePrice(15,2));
        assertEquals(230.0,PricingCalculator.calculatePrice(23,2));
    }

    @Test
    public void testHourlyRateWithDiscount(){
        assertEquals(10.5,PricingCalculator.calculatePrice(1,3));
        assertEquals(157.5,PricingCalculator.calculatePrice(15,3));
        assertEquals(241.5,PricingCalculator.calculatePrice(23,3));
        assertEquals(402.5,PricingCalculator.calculatePrice(23,5));
    }

    @Test
    public void testDailyRateForOneCustomer() {
        assertEquals(20.0,PricingCalculator.calculatePrice(24,1));
        assertEquals(60.0,PricingCalculator.calculatePrice(72,1));
        assertEquals(120.0,PricingCalculator.calculatePrice(144,1));
    }

    @Test
    public void testDailyRateForTwoCustomer(){
        assertEquals(40.0,PricingCalculator.calculatePrice(24,2));
        assertEquals(120.0,PricingCalculator.calculatePrice(72,2));
        assertEquals(240.0,PricingCalculator.calculatePrice(144,2));
    }

    @Test
    public void testDailyRateWithDiscount(){
        assertEquals(42.0,PricingCalculator.calculatePrice(24,3));
        assertEquals(126.0,PricingCalculator.calculatePrice(72,3));
        assertEquals(252.0,PricingCalculator.calculatePrice(144,3));
        assertEquals(420.0,PricingCalculator.calculatePrice(144,5));
    }

    @Test
    public void testWeeklyRateForOneCustomer() {
        assertEquals(60.0,PricingCalculator.calculatePrice(168,1));
        assertEquals(120.0,PricingCalculator.calculatePrice(336,1));
        assertEquals(240.0,PricingCalculator.calculatePrice(672,1));
    }

    @Test
    public void testWeeklyRateForTwoCustomer(){
        assertEquals(120.0,PricingCalculator.calculatePrice(168,2));
        assertEquals(240.0,PricingCalculator.calculatePrice(336,2));
        assertEquals(480.0,PricingCalculator.calculatePrice(672,2));
    }

    @Test
    public void testWeeklyRateWithDiscount(){
        assertEquals(126.0,PricingCalculator.calculatePrice(168,3));
        assertEquals(252.0,PricingCalculator.calculatePrice(336,3));
        assertEquals(504.0,PricingCalculator.calculatePrice(672,3));
        assertEquals(840.0,PricingCalculator.calculatePrice(672,5));
    }

    @Test
    public void testHourlyDailyWeeklyRateForOneCustomer() {
        assertEquals(85.0,PricingCalculator.calculatePrice(193,1));
        assertEquals(230.0,PricingCalculator.calculatePrice(418,1));
        assertEquals(475.0,PricingCalculator.calculatePrice(839,1));
    }

    @Test
    public void testHourlyDailyWeeklyRateForTwoCustomer(){
        assertEquals(170.0,PricingCalculator.calculatePrice(193,2));
        assertEquals(460.0,PricingCalculator.calculatePrice(418,2));
        assertEquals(950.0,PricingCalculator.calculatePrice(839,2));
    }

    @Test
    public void testHourlyDailyWeeklyRateWithDiscount(){
        assertEquals(178.5,PricingCalculator.calculatePrice(193,3));
        assertEquals(483.0,PricingCalculator.calculatePrice(418,3));
        assertEquals(997.5,PricingCalculator.calculatePrice(839,3));
        assertEquals(1662.5,PricingCalculator.calculatePrice(839,5));
    }
}
