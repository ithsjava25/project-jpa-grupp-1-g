package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.Optional;

public class DirectorRepositoryImpl extends BaseRepositoryImpl<Director> implements DirectorRepository<Director> {

    public DirectorRepositoryImpl(EntityManagerFactory em) {

        super(em.createEntityManager(), Director.class);
    }

    @Override
    public Optional<Director> findByName(String name) {
        try {
        return em.createQuery("SELECT d FROM Director d WHERE d.name = :name", Director.class)
            .setParameter("name", name)
            .getResultStream()
            .findFirst();
        } catch (Exception e) {
        System.err.println("Error: " + e.getMessage());

        } finally {
                   em.close();
               }

        return Optional.empty();
    }
}
