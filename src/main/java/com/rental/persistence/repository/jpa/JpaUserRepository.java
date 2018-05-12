package com.rental.persistence.repository.jpa;

import com.rental.persistence.model.entities.User;
import com.rental.persistence.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class JpaUserRepository implements UserRepository {

    private final static Logger LOGGER = LogManager.getLogger(JpaUserRepository.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public User createUser(User user) {
        LOGGER.debug("Persisting new user with data: " + user.toString());
        entityManager.persist(user);
        return user;
    }

    @Override
    public User getUserByName(String name) {
        LOGGER.debug("Retrieving user with name: " + name);
        Query query = entityManager.createQuery("SELECT a FROM User a WHERE a.name=?1");
        query.setParameter(1, name);
        List<User> users = query.getResultList();
        if(users.size() == 0) {
            return null;
        } else {
            return users.get(0);
        }
    }

    @Override
    public User getUserById(Long id) {
        LOGGER.debug("Retrieving user with id: " + id);
        return entityManager.find(User.class, id);
    }


}
