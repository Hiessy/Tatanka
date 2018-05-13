package com.rental.persistence;

import com.rental.config.DataSourceConfig;
import com.rental.persistence.model.entities.User;
import com.rental.persistence.repository.UserRepository;
import com.rental.persistence.repository.jpa.JpaUserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.Assert.assertEquals;

/**
 * <p>Testing user repository. For simplicity porpoises the same environment is used.</p>
 *
 * @author Martín Díaz
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JpaUserRepository.class, DataSourceConfig.class})
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    User user;

    @Before
    @Transactional
    @Rollback
    public void setup()
    {
        user = new User();
        user.setName("Martin");
        user.setPassword("password");
        userRepository.createUser(user);
    }

    @Test
    @Transactional
    public void getUserByIdTest()
    {
        User user = userRepository.getUserById(this.user.getId());
        assertNotNull(user);
        assertEquals(user.getName(), "Martin");
        assertEquals(user.getPassword(), "password");
    }
}
