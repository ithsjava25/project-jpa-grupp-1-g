package org.example;

import java.util.Optional;
import java.util.Set;

public interface SeriesRepository<T extends Series> extends Repository<T> {
    Optional<Series> findByTitle(String title);

    Set<Director> findDirectors(Series series);
}
