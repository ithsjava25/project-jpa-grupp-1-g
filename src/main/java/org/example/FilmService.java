package org.example;



public class FilmService {

    private final FilmRepository<Film> filmRepository;

    public FilmService(FilmRepository filmRepository, DirectorRepository directorRepo) {
        this.filmRepository = filmRepository;
    }


    public Film create(Film film) {
        return (Film) filmRepository.save(film);
    }

    // TODO: Ändra till addDirector??
    public void addFilm(Long directorId, Film film) {
        Director director = (Director) directorRepository.findById(directorId)
            .orElseThrow();

        director.addFilm(film);
    }


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

    public Film findFilm(Long id) {
        Film film = (Film) filmRepository.findById(id)
            .orElseThrow();

        return film;

    }
}
