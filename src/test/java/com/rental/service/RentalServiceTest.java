package com.rental.service;

import com.rental.persistence.model.entities.Rental;
import com.rental.persistence.model.entities.User;
import com.rental.persistence.repository.RentalRepository;
import com.rental.service.impl.RentalServiceImpl;
import com.rental.service.utils.RentalList;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * <p>Testing the rental service object in the service layer.</p>
 *
 * @author Martín Diaz
 * @version 1.0
 */
public class RentalServiceTest {

    @InjectMocks
    private RentalServiceImpl rentalService;

    @Mock
    private RentalRepository rentalRepository;
    @Before
    public void setup() {

        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void getPricingTest(){

        Rental rental = new Rental();
        rental.setTime(2);
        rental.setNumberOfBikes(2);
        rentalService.getPricing(rental);

        assertEquals(new Double(20.0),rental.getPrice());
    }

    @Test
    public void getAllRentalsTest(){

        when(rentalRepository.findAllRentals()).thenReturn(setupRentalList());

        RentalList rentalList = rentalService.getAllRentals();

        assertEquals(2,rentalList.getRentals().size());

    }
    @Test
    public void removeRentalTest(){

        User user = new User("Martin", "password");
        user.setId(1L);
        Rental rental = new Rental();
        rental.setId(2L);
        rental.setTime(2);
        rental.setNumberOfBikes(2);
        rental.setPrice(20.0);
        rental.setUser(user);
        rental.setRentalDateRequested(LocalDateTime.now().plusDays(2));

        when(rentalRepository.deleteRental(any(Long.class))).thenReturn(rental);

        Rental deletedRental = rentalService.removeRental(any(Long.class));

        assertEquals(new Long(1L),deletedRental.getUser().getId());
        assertEquals("Martin",deletedRental.getUser().getName());
        assertEquals("password", deletedRental.getUser().getPassword());

        assertEquals(new Long(2L),deletedRental.getId());
        assertEquals(new Integer(2),deletedRental.getNumberOfBikes());
        assertEquals(new Integer(2),deletedRental.getTime());
        assertEquals(new Double(20.0),deletedRental.getPrice());
    }

    private List<Rental> setupRentalList() {
        List<Rental> rentals = new ArrayList<>();

        Rental rentalA = new Rental(2, 1,10.0, LocalDateTime.now().plusDays(2));
        Rental rentalB = new Rental(1, 2, 10.0, LocalDateTime.now().plusDays(2));
        User userA = new User("Martin", "password");
        User userB = new User("Juan", "password");

        userA.setId(1L);

        userB.setId(2L);

        rentalA.setId(10L);
        rentalA.setUser(userA);

        rentalB.setId(20L);
        rentalB.setUser(userB);

        rentals.add(rentalA);
        rentals.add(rentalB);

        return rentals;

    }
}
