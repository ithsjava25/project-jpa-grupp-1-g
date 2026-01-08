package org.example;



public class DirectorService {

    private final DirectorRepository<Director> directorRepository;

    public DirectorService(DirectorRepository directorRepository, FilmRepository filmRepo) {
        this.directorRepository = directorRepository;
    }


    public Director create(Director director) {
        return (Director) directorRepository.save(director);
    }


    public void addFilm(Long directorId, Film film) {
        Director director = (Director) directorRepository.findById(directorId)
            .orElseThrow();

        director.addFilm(film);
    }


    public DirectorDTO find(Long id) {
        return directorRepository.findById(id)
            .map(d -> new DirectorDTO(d.getName(), d.getCountry()))
            .orElseThrow(() -> new RuntimeException("Director not found"));
    }


    public void delete(Long id) {
        Director director = (Director) directorRepository.findById(id)
            .orElseThrow();

        directorRepository.delete(director);
    }

    public Director findDirector(Long id) {
        Director director = (Director) directorRepository.findById(id)
            .orElseThrow();

        return director;

    }
}
