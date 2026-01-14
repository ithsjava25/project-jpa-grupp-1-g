package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

import java.util.Optional;
import java.util.Set;

public class SeriesRepositoryImpl extends BaseRepositoryImpl<Series> implements SeriesRepository<Series> {

    private final EntityManagerFactory emf;

    public SeriesRepositoryImpl(EntityManagerFactory em) {
        super(Series.class);
        this.emf = em;
        super.setEntityManager(emf);
    }

    @Override
    public Optional<Series> findByTitle(String title){
        Optional<Series> entity = Optional.empty();
        try (EntityManager em = emf.createEntityManager()){
            EntityTransaction entityTransaction = em.getTransaction();
            entityTransaction.begin();
            entity = em.createQuery("SELECT s FROM Series s WHERE s.title = :title", Series.class)
                .setParameter("title", title)
                .getResultStream()
                .findFirst();
            em.getTransaction().commit();
        } catch (Exception _) {
        }

        return entity;
    }

    @Override
    public Set<Director> findDirectors(Series series) {
        EntityManager em = emf.createEntityManager();
        Optional<Series> entity = Optional.empty();
        try {
            EntityTransaction entityTransaction = em.getTransaction();
            entityTransaction.begin();
            long id = series.getId();
            entity = em.createQuery("SELECT s FROM Series s JOIN FETCH s.directors d WHERE s.id = :id", Series.class)
                .setParameter("id", id)
                .getResultStream()
                .findFirst();
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }

        return entity.map(Series::getDirectors).orElse(Set.of());
    }
}
