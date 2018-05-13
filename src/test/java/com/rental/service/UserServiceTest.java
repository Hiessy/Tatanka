package com.rental.service;

import com.rental.persistence.model.entities.Rental;
import com.rental.persistence.model.entities.User;
import com.rental.persistence.repository.RentalRepository;
import com.rental.persistence.repository.UserRepository;
import com.rental.service.exception.InvalidDateException;
import com.rental.service.exception.UserDoesNotExistsException;
import com.rental.service.exception.UserExistsException;
import com.rental.service.impl.UserServiceImpl;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * <p>Testing the user service object in the service layer.</p>
 *
 * @author Martín Diaz
 * @version 1.0
 */
public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RentalRepository rentalRepository;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setup() {

        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void createNewUserTest() {
        User user = new User("Martin", "password");
        user.setId(1L);

        when(userRepository.getUserByName(any(String.class))).thenReturn(null);
        when(userRepository.createUser(any(User.class))).thenReturn(user);

        User createdUser = userService.createUser(user);

        assertEquals(user.getId(), createdUser.getId());
    }

    @Test
    public void createExistingUserTest() {
        User user = new User("Martin", "password");
        user.setId(1L);

        when(userRepository.getUserByName(any(String.class))).thenReturn(user);
        thrown.expect(UserExistsException.class);
        userService.createUser(user);

    }

    @Test
    public void getUserTest() {
        User user = new User("Martin", "password");
        user.setId(1L);

        when(userRepository.getUserByName("Martin")).thenReturn(user);

        User returnedUser = userService.getUser("Martin");

        assertEquals(new Long(1L),returnedUser.getId());
        assertEquals("Martin",returnedUser.getName());
        assertEquals("password",returnedUser.getPassword());
    }

    @Test
    public void createNewRentalForUserTest() {
        User user = new User("Martin", "password");
        user.setId(1L);
        Rental rental = new Rental();
        rental.setId(2L);
        rental.setTime(2);
        rental.setNumberOfBikes(2);
        rental.setRentalDateRequested(LocalDateTime.now().plusDays(2));

        when(userRepository.getUserById(any(Long.class))).thenReturn(user);
        when(rentalRepository.createRental(any(Rental.class))).thenReturn(rental);

        Rental createdRental = userService.createRental(user.getId(), rental);

        assertEquals(new Double(20.0), createdRental.getPrice());
        assertEquals(user.getName(), createdRental.getUser().getName());

    }

    @Test
    public void createNewRentalForUserInvalidDateTest() {
        Rental rental = new Rental();
        rental.setRentalDateRequested(LocalDateTime.now().minusDays(1L));

        thrown.expect(InvalidDateException.class);
        userService.createRental(1L, rental);
    }

    @Test
    public void createNewRentalUserDoesNotExistTest() {
        Rental rental = new Rental();
        rental.setRentalDateRequested(LocalDateTime.now().plusDays(2));

        when(userRepository.getUserById(any(Long.class))).thenReturn(null);

        thrown.expect(UserDoesNotExistsException.class);
        userService.createRental(any(Long.class), rental);
    }

}
