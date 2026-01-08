package org.example;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceConfiguration;
import org.hibernate.jpa.HibernatePersistenceConfiguration;

import java.util.List;

public class Main {
        public static void main(String[] args) {


            final PersistenceConfiguration cfg = new HibernatePersistenceConfiguration("emf")
                .jdbcUrl("jdbc:mysql://localhost:3306/film_database")
                .jdbcUsername("root")
                .jdbcPassword("root")
                .property("hibernate.hbm2ddl.auto", "update")
                .property("hibernate.show_sql", "true")
                .property("hibernate.format_sql", "true")
                .property("hibernate.highlight_sql", "true")
                .managedClasses(Film.class, Director.class);
            try (EntityManagerFactory emf = cfg.createEntityManagerFactory()) {
                emf.runInTransaction(em -> {
                    //Skapa regissör för test
                    Director director = new Director();
                    director.setName("Christopher Bolan");
                    director.setCountry("United States");
                    director.setBirthYear(1970);
                    em.persist(director);

                    //Testa att lägga till filmer
                    Film.addFilm(em, "The Dirk Knight", director);
                    Film.addFilm(em, "Inception", director);

                    //Testa att hämta alla filmer
                    System.out.println("\nAlla filmer:");
                    List<Film> films = Film.getAllFilms(em);
                    films.forEach(film -> System.out.println(film.getTitle()));

                    //Testa att hämta filmer av en viss regissör
                    System.out.println("\nAlla filmer av regissör:");
                    List<Film> filmsByDirector = Film.getFilmsByDirector(em, director);
                    filmsByDirector.forEach(film -> System.out.println(film.getTitle()));

                    //If no Films in database, add some
//                    if (em.createQuery("select count(o) from org.example.Film o", Long.class)
//                        .getSingleResult() == 0) {
//                        org.example.Film film1 = new org.example.Film();
//                        em.persist(film1);
//                        em.flush();
//                        org.example.Film film2 = new org.example.Film();
//                        em.persist(film2);
//                    }
//                    System.out.println("==== Using select query, N + 1 ====");
//                    em.createQuery("from org.example.Film", org.example.Film.class)
//                        .getResultList().forEach(System.out::println);

                });

            }

        }
}
