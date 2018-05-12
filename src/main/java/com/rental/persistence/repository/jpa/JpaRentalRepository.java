package com.rental.persistence.repository.jpa;


import com.rental.persistence.model.entities.Rental;
import com.rental.persistence.repository.RentalRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class JpaRentalRepository implements RentalRepository {

    private final static Logger LOGGER = LogManager.getLogger(JpaRentalRepository.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Rental createRental(Rental rental) {
        LOGGER.debug("Persisting rental data: " + rental.toString());
        entityManager.persist(rental);
        return rental;
    }

    @Override
    public List<Rental> findAllRentals() {
        LOGGER.debug("Retrieving all rental data");
        Query query = entityManager.createQuery("SELECT r FROM Rental r");
        return query.getResultList();
    }

    @Override
    @Transactional
    public Rental deleteRental(Long rentalId) {
        LOGGER.debug("Deleting rental data for id " + rentalId);
        Rental rental = entityManager.find(Rental.class, rentalId);
        if (rental != null)
            entityManager.remove(rental);
        return rental;
    }

}
