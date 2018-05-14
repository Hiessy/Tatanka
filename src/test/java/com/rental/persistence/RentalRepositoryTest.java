package com.rental.persistence;

import com.rental.config.DataSourceConfig;
import com.rental.persistence.model.entities.Rental;
import com.rental.persistence.model.entities.User;
import com.rental.persistence.repository.RentalRepository;
import com.rental.persistence.repository.UserRepository;
import com.rental.persistence.repository.jpa.JpaRentalRepository;
import com.rental.persistence.repository.jpa.JpaUserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.assertEquals;

/**
 * <p>Test for rental repository. For simplicity porpoises the same environment is used.</p>
 *
 * @author Martín Díaz
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JpaUserRepository.class, JpaRentalRepository.class, DataSourceConfig.class})
public class RentalRepositoryTest {

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private UserRepository userRepository;

    private Rental rental;
    private User user;
    private Long rentalId;

    @Before
    @Transactional
    @Rollback
    public void setup() {
        user = new User();
        user.setName("Martin");
        user.setPassword("password");
        user.setId(userRepository.createUser(user).getId());

        rental = new Rental();
        rental.setTime(1);
        rental.setNumberOfBikes(1);
        rental.setPrice(5.0);
        rental.setRentalDateRequested(LocalDateTime.now().plusDays(2));
        rental.setUser(user);

        rentalId = rentalRepository.createRental(rental).getId();
    }

    @Test
    public void getUserByIdTest() {
        List<Rental> rentals = rentalRepository.findAllRentals();
        assertNotNull(rentals);
        assertEquals(rentalId, rentals.get(rentals.size() - 1).getId());

    }
}
