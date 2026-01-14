package org.example;

import java.util.Optional;
import java.util.Set;

public interface DirectorRepository<T extends Director> extends Repository<T> {
    Optional<Director> findByName(String name);

    Set<Film> findFilms(T director);

    Set<Series> findSeries(T director);

    void addSeries(T director, Series series);
}
