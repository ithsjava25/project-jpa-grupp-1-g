package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

import java.util.Optional;

public class FilmRepositoryImpl extends BaseRepositoryImpl<Film> implements FilmRepository<Film> {

    private final EntityManagerFactory emf;

    public FilmRepositoryImpl(EntityManagerFactory em) {
        super(Film.class);
        this.emf = em;
        super.setEntityManager(emf);
    }

    @Override
    public Optional<Film> findByTitle(String title) {
        Optional<Film> entity = Optional.empty();
        try (EntityManager em = emf.createEntityManager()){
            EntityTransaction entityTransaction = em.getTransaction();
            entityTransaction.begin();
            entity = em.createQuery("SELECT f FROM Film f WHERE f.title = :title", Film.class)
                .setParameter("title", title)
                .getResultList().stream()
                .findFirst();
            em.getTransaction().commit();
        } catch (Exception _) {
        }

        return entity;
    }
}
