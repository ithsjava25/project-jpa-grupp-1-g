package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.transaction.Transactional;

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
//        if (entity == null) {
//                throw new IllegalArgumentException("Entity cannot be null");
//            }
//            if (entity.getId() == null) {
//                em.persist(entity);
//                return entity;
//            } else {
//            return em.merge(entity);
//        }

        EntityManager em = emf.createEntityManager();
        EntityTransaction entityTransaction = em.getTransaction();
        entityTransaction.begin();
        if (entity.getId() == null) {
            em.persist(entity);
        } else {
            em.merge(entity);
        }
        em.getTransaction().commit();
        em.close();
        return entity;
    }


    @Override
    public Optional<T> findById(Long id) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction entityTransaction = em.getTransaction();
        entityTransaction.begin();
        Optional<T> entity = Optional.ofNullable(em.find(entityClass, id));
        em.getTransaction().commit();
        em.close();
        return entity;
    }

    @Override
    public void delete(T entity) {

        EntityManager em = emf.createEntityManager();
        EntityTransaction entityTransaction = em.getTransaction();
        entityTransaction.begin();
        em.remove(em.contains(entity) ? entity : em.merge(entity));
        em.getTransaction().commit();
        em.close();

    }

    @Override
    public void deleteById(Long id) {

        EntityManager em = emf.createEntityManager();
        EntityTransaction entityTransaction = em.getTransaction();
        entityTransaction.begin();
        T entity = em.find(entityClass, id);
        if (entity != null) {
            em.remove(entity);
        }
        em.getTransaction().commit();
        em.close();

    }

    @Override
    public Iterable<T> findAll() {

        EntityManager em = emf.createEntityManager();
        EntityTransaction entityTransaction = em.getTransaction();
        entityTransaction.begin();
        Iterable<T> entity = em.createQuery(
            "select e from " + entityClass.getSimpleName() + " e", entityClass
        ).getResultList();
        em.getTransaction().commit();
        em.close();

        return entity;
    }

    @Override
    public boolean existsById(Long id) {

        EntityManager em = emf.createEntityManager();
        EntityTransaction entityTransaction = em.getTransaction();
        entityTransaction.begin();
        Long count = em.createQuery(
            "select count(e) from " + entityClass.getSimpleName() + " e where e.id = :id",
            Long.class
        ).setParameter("id", id).getSingleResult();
        em.getTransaction().commit();
        em.close();

        return count > 0;

    }

    @Override
    public long count() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction entityTransaction = em.getTransaction();
        entityTransaction.begin();
        long count = em.createQuery("select count(e) from " + entityClass.getSimpleName() + " e", Long.class).getSingleResult();
        em.getTransaction().commit();
        em.close();
        return count;
    }

    @Override
    public void flush() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction entityTransaction = em.getTransaction();
        entityTransaction.begin();
        em.flush();
        em.getTransaction().commit();
        em.close();
    }

    @Override
    public void clear() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction entityTransaction = em.getTransaction();
        entityTransaction.begin();
        em.clear();
        em.getTransaction().commit();
        em.close();
    }

}
