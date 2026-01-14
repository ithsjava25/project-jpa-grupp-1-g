package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

import java.util.Optional;
import java.util.Set;

//public class DirectorRepositoryImpl extends BaseRepositoryImpl<Director> implements DirectorRepository<Director> {

public class DirectorRepositoryImpl extends BaseRepositoryImpl<Director> implements DirectorRepository<Director> {
    private final EntityManagerFactory emf;

    public DirectorRepositoryImpl(EntityManagerFactory em) {

        //super(em.createEntityManager(), Director.class);
        super(Director.class);
        this.emf = em;
        super.setEntityManager(emf);
    }

    @Override
    public Optional<Director> findByName(String name) {
        Optional<Director> entity = Optional.empty();
        try (EntityManager em = emf.createEntityManager()){
            EntityTransaction entityTransaction = em.getTransaction();
            entityTransaction.begin();
            entity = em.createQuery("SELECT d FROM Director d WHERE d.name = :name", Director.class)
                .setParameter("name", name)
                .getResultStream()
                .findFirst();
            em.getTransaction().commit();
        } catch (Exception _) {
        }

        return entity;
    }

    @Override
    public Set<Film> findFilms(Director director) {
        Optional<Director> entity = Optional.empty();
        try (EntityManager em = emf.createEntityManager()){
            EntityTransaction entityTransaction = em.getTransaction();
            entityTransaction.begin();
            long id = director.getId();
            entity = em.createQuery("SELECT d FROM Director d JOIN FETCH d.films f WHERE d.id = :id", Director.class)
                .setParameter("id", id)
                .getResultStream()
                .findFirst();
            em.getTransaction().commit();
        } catch (Exception _) {
        }

        return entity.map(Director::getFilms).orElse(null);
    }

    @Override
    public Set<Series> findSeries(Director director) {
        Optional<Director> entity = Optional.empty();
        try (EntityManager em = emf.createEntityManager()){
            EntityTransaction entityTransaction = em.getTransaction();
            entityTransaction.begin();
            long id = director.getId();
            entity = em.createQuery("SELECT d FROM Director d JOIN FETCH d.series s WHERE d.id = :id", Director.class)
                .setParameter("id", id)
                .getResultStream()
                .findFirst();
            em.getTransaction().commit();
        } catch (Exception _) {
        }

        return entity.map(Director::getSeries).orElse(null);
    }

    @Override
    public void addSeries(Director director, Series series) {
        EntityManager em = emf.createEntityManager();
        try {
            EntityTransaction entityTransaction = em.getTransaction();
            entityTransaction.begin();
            Series s = em.find(Series.class, series.getId());
            Director d = em.find(Director.class, director.getId());
            d.addSeries(s);
            em.merge(d);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
}
