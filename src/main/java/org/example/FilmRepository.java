package org.example;

import java.util.Optional;

public interface FilmRepository<B> extends Repository<Film> {

    Optional<Film> findByName(String name);

}
