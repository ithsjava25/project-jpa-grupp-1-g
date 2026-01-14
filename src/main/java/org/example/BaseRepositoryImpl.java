package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

import java.util.ArrayList;
import java.util.Optional;

public class BaseRepositoryImpl<T extends org.example.BaseEntity> implements Repository<T> {

    protected EntityManagerFactory emf;
    protected Class<T> entityClass;


    public BaseRepositoryImpl(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public void setEntityManager(EntityManagerFactory emf) {
        this.emf = emf;
    }



    @Override
    public T save(T entity) {
        try (EntityManager em = emf.createEntityManager()) {
            EntityTransaction entityTransaction = em.getTransaction();
            entityTransaction.begin();
            if (entity.getId() == null) {
                em.persist(entity);
            } else {
                em.merge(entity);
            }
            em.getTransaction().commit();

        } catch (Exception _) {
        }
        return entity;
    }


    @Override
    public Optional<T> findById(Long id) {
        Optional<T> entity = Optional.empty();
        try (EntityManager em = emf.createEntityManager()){
            EntityTransaction entityTransaction = em.getTransaction();
            entityTransaction.begin();
            entity = Optional.ofNullable(em.find(entityClass, id));
            em.getTransaction().commit();
        } catch (Exception _) {
        }
        return entity;
    }

    @Override
    public void delete(T entity) {

        try (EntityManager em = emf.createEntityManager()) {
            EntityTransaction entityTransaction = em.getTransaction();
            entityTransaction.begin();
            em.remove(em.contains(entity) ? entity : em.merge(entity));
            em.getTransaction().commit();
        } catch (Exception _) {
        }

    }

    @Override
    public void deleteById(Long id) {

        try (EntityManager em = emf.createEntityManager()){
            EntityTransaction entityTransaction = em.getTransaction();
            entityTransaction.begin();
            T entity = em.find(entityClass, id);
            if (entity != null) {
                em.remove(entity);
            }
            em.getTransaction().commit();
        } catch (Exception _) {
        }
    }

    @Override
    public Iterable<T> findAll() {

        Iterable<T> entities = new ArrayList<>();
        try (EntityManager em = emf.createEntityManager()){
            EntityTransaction entityTransaction = em.getTransaction();
            entityTransaction.begin();
            entities = em.createQuery(
                "select e from " + entityClass.getSimpleName() + " e", entityClass
            ).getResultList();
            em.getTransaction().commit();
        } catch (Exception _) {
        }

        return entities;
    }

    @Override
    public boolean existsById(Long id) {

        long count = 0;
        try (EntityManager em = emf.createEntityManager()){
            EntityTransaction entityTransaction = em.getTransaction();
            entityTransaction.begin();
            count = em.createQuery(
                "select count(e) from " + entityClass.getSimpleName() + " e where e.id = :id",
                Long.class
            ).setParameter("id", id).getSingleResult();
            em.getTransaction().commit();
        } catch (Exception _) {
        }

        return count > 0;

    }

    @Override
    public long count() {
        long count = 0;
        try (EntityManager em = emf.createEntityManager()){
            EntityTransaction entityTransaction = em.getTransaction();
            entityTransaction.begin();
            count = em.createQuery("select count(e) from " + entityClass.getSimpleName() + " e", Long.class).getSingleResult();
            em.getTransaction().commit();
        } catch (Exception _) {
        }
        return count;
    }
}
