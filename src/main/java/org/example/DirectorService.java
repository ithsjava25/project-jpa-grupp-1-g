package org.example;

import jakarta.transaction.Transactional;

public class DirectorService {

    private final DirectorRepository<Director> directorRepository;

    public DirectorService(DirectorRepository directorRepository, FilmRepository filmRepo) {
        this.directorRepository = directorRepository;
    }

    @Transactional
    public Director create(Director director) {
        return (Director) directorRepository.save(director);
    }

    @Transactional
    public void addFilm(Long directorId, Film film) {
        Director director = (Director) directorRepository.findById(directorId)
            .orElseThrow();

        director.addFilm(film);
    }

    @Transactional
    public DirectorDTO find(Long id) {
        return directorRepository.findById(id)
            .map(d -> new DirectorDTO(d.getName(), d.getCountry()))
            .orElseThrow(() -> new RuntimeException("Director not found"));
    }

    @Transactional
    public void delete(Long id) {
        Director director = (Director) directorRepository.findById(id)
            .orElseThrow();

        directorRepository.delete(director);
    }
    @Transactional
    public Director findDirector(Long id) {
        Director director = (Director) directorRepository.findById(id)
            .orElseThrow();

        return director;

    }
}
