package org.example;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    @ManyToOne
    @JoinColumn(name = "director_id")
    private Director director;

    public Director getDirector() {
        return director;
    }

    public void setDirector(Director director) {
        this.director = director;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    //Lägg till en ny film
    public static void addFilm(EntityManager em, String title, Director director) {
        Film film = new Film();
        film.setTitle(title);
        film.setDirector(director);
        em.persist(film);
        System.out.println("org.example.Film tillagd: " + title);
    }
    //Hämta alla filmer
    public static List<Film> getAllFilms(EntityManager em) {
        return em.createQuery("FROM Film", Film.class).getResultList();
    }

    //Hämta filmer regisserade av en viss regissör
    public static List<Film> getFilmsByDirector(EntityManager em, Director director) {
        return em.createQuery("FROM Film f WHERE f.director = :director", Film.class)
            .setParameter("director", director)
            .getResultList();
    }


}
