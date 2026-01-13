package org.example;

import jakarta.persistence.EntityManagerFactory;

import java.util.Optional;

public class SeriesRepositoryImpl extends BaseRepositoryImpl<Series> implements SeriesRepository<Series> {

    public SeriesRepositoryImpl(EntityManagerFactory em) {

        super(em.createEntityManager(), Series.class);
    }

    @Override
    public Optional<Series> findByName(String title){
        return em.createQuery("SELECT s FROM Series s WHERE s.title = :title", Series.class)
            .setParameter("title", title)
            .getResultStream()
            .findFirst();
    }
}
