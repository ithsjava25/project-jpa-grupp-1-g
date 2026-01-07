package org.example;

import jakarta.persistence.EntityManager;

import java.util.Optional;

public class DirectorRepositoryImpl extends BaseRepositoryImpl<Director> implements DirectorRepository<Director> {

    public DirectorRepositoryImpl(EntityManager em) {
        super(em, Director.class);
    }

    @Override
    public Optional<Director> findByName(String name) {

        return em.createQuery("SELECT d FROM Director d WHERE d.name = :name", Director.class)
            .setParameter("name", name)
            .getResultStream()
            .findFirst();
    }


}
