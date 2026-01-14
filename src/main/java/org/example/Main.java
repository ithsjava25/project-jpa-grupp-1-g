package org.example;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;
import jakarta.persistence.*;
import org.hibernate.jpa.HibernatePersistenceConfiguration;

import java.util.List;

public class Main {
     static void main() {
         List<Class<?>> entities = getEntities("org.example");

        final PersistenceConfiguration cfg = new HibernatePersistenceConfiguration("emf")
            .jdbcUrl("jdbc:mysql://localhost:3306/mysql")
            .jdbcUsername("root")
            .jdbcPassword("root")
            .property("hibernate.hbm2ddl.auto", "update")
            .property("hibernate.show_sql", "false")
            .property("hibernate.format_sql", "true")
            .property("hibernate.highlight_sql", "true")
            .managedClasses(entities);

         try (EntityManagerFactory emf = cfg.createEntityManagerFactory()) {

             DirectorRepositoryImpl directorRepository = new DirectorRepositoryImpl(emf);
             DirectorService directorService = new DirectorService(directorRepository);

             FilmRepositoryImpl filmRepository = new FilmRepositoryImpl(emf);
             FilmService filmService = new FilmService(filmRepository);

             SeriesRepositoryImpl seriesRepository = new SeriesRepositoryImpl(emf);
             SeriesService seriesService = new SeriesService(seriesRepository);

             CLI cli = new CLI();
             cli.cliStart(directorService, filmService, seriesService);
         }
     }
//             emf.runInTransaction(em -> {
//                 if (!directorRepository.findAll().iterator().hasNext()) {
//                     Director d = new Director();
//                     d.setName("John Doe");
//                     directorRepository.save(d);
//                 }
//             });

//             if (!directorRepository.findAll().iterator().hasNext()){
//                 EntityManager entityManager = emf.createEntityManager();
//                 EntityTransaction entityTransaction = entityManager.getTransaction();
//                 entityTransaction.begin();
//                 Director director = new Director();
//                 director.setName("John Doeson");
//                 director.setCountry("USA");
//                 director.setBirthYear(1970);
//                 entityManager.persist(director);
//                 entityManager.getTransaction().commit();
//                 entityManager.close();
//             }


    private static List<Class<?>> getEntities(String pkg) {
        List<Class<?>> entities;

        try (ScanResult scanResult =
                 new ClassGraph()
                     .enableClassInfo()
                     .enableAnnotationInfo()
                     .acceptPackages(pkg)
                     .scan()) {

            entities = scanResult.getClassesWithAnnotation(Entity.class).loadClasses();
        }
        return entities;
    }

}
