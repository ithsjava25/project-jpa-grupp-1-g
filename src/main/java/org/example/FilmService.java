package org.example;

import jakarta.transaction.Transactional;

public class FilmService {

    private final FilmRepository<Film> filmRepository;

    public FilmService(FilmRepository filmRepository, DirectorRepository directorRepo) {
        this.filmRepository = filmRepository;
    }

    @Transactional
    public Film create(Film film) {
        return (Film) filmRepository.save(film);
    }

    @Transactional
    public void addDirector(Long FilmId, Director director) {
        Film film = (Film) filmRepository.findById(FilmId)
            .orElseThrow();

        film.addDirector(director);
    }

    @Transactional
    public FilmDTO find(Long id) {
        return filmRepository.findById(id)
            .map(d -> new FilmDTO(d.getTitle()))
            .orElseThrow(() -> new RuntimeException("Film not found"));
    }

    // Behövs denna?
//    public void delete(Long id) {
//        Director director = (Director) directorRepository.findById(id)
//            .orElseThrow();
//
//        directorRepository.delete(director);
//    }
    @Transactional
    public Film findFilm(Long id) {
        Film film = (Film) filmRepository.findById(id)
            .orElseThrow();

        return film;

    }
}
