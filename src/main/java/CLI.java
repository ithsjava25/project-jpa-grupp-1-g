import jakarta.persistence.EntityManager;

import java.io.IOException;

public class CLI {
    private final String os = System.getProperty("os.name");
    EntityManager em;

    void mainMenu(EntityManager entityManager) { //throws IOException, InterruptedException {
        while(true) {
            //clearConsole();
            em = entityManager;
            System.out.println("""
                Welcome to the Film Database!

                Please select a category to view below:

                1. Directors
                2. Films
                3. Series

                4. Exit

                """);
            switch (IO.readln()) {
                case "1" -> directorMenu();
                case "2" -> filmMenu();
                case "3" -> seriesMenu();
                case "4" -> System.exit(0);
                default -> invalidInput();
            }
        }
    }

    private static void invalidInput() {
        System.out.println("Invalid input!" +
            "PLease enter the number of the option you wish to choose");
    }

    private void directorMenu() { //throws IOException, InterruptedException {
        //clearConsole();
        System.out.println("""
            You are in the Director Menu.

            Please select an action below:

            1. Create new entry
            2. List entries
            3. List specific entry
            4. Update existing entry

            5. Exit

            """);
        switch (IO.readln()){
            case "1" -> createDirector();
            case "2" -> listDirectors();
            case "3" -> listSpecificDirector();
            case "4" -> updateDirector();
            case "5" -> mainMenu(em);
            default -> invalidInput();
        }
    }

    private void createDirector() { //throws IOException, InterruptedException {
        //clearConsole();
        String name = IO.readln("Enter the full name of the Director: ");
        String country = IO.readln("Enter the country of the Director: ");
        int birthYear =  Integer.parseInt(IO.readln("Enter the birth year of the Director: "));
        Integer yearOfDeath = Integer.valueOf(IO.readln("Enter the year of death of the Director." +
                                                                "If they're alive, leave blank: "));

        //TODO:
        //send information to create-method in Director class
    }

    private void listDirectors() {
        IO.println(Director.findAll(em));
    }

    private void listSpecificDirector() {
        String name = IO.readln("Enter the name of the Director: ");
        IO.println(Director.findByName(em, name));
    }

    private void updateDirector() {
        IO.println("When prompted, enter the value you wish to update." +
            "If you don't want to change it, leave the input blank.");

        String name = IO.readln("Enter the full name of the Director: ");
        String country = IO.readln("Enter the country of the Director: ");
        Integer birthYear =  Integer.valueOf(IO.readln("Enter the birth year of the Director: "));
        Integer yearOfDeath = Integer.valueOf(IO.readln("Enter the year of death of the Director: "));

        //TODO:
        //Determine which values aren't null, and update them
    }

    private void filmMenu() { //throws IOException, InterruptedException {
        //clearConsole();
        System.out.println("""
            You are in the Film Menu.

            Please select an action below:

            1. Create new entry
            2. List entries
            3. List specific entry
            4. Update existing entry

            5. Exit

            """);
        switch (IO.readln()){
            case "1" -> createFilm();
            case "2" -> listFilms();
            case "3" -> listSpecificFilm();
            case "4" -> updateFilm();
            case "5" -> mainMenu(em);
            default -> invalidInput();
        }
    }

    private void createFilm() { //throws IOException, InterruptedException {
        //clearConsole();
        String title = IO.readln("Enter the title of the Film: ");

        //TODO:
        //send information to create-method in Film class
    }

    private void listFilms() {
        IO.println(Film.findAll(em));
    }

    private void listSpecificFilm() {
        String title = IO.readln("Enter the title of the Film: ");
        IO.println(Film.findByName(em, title));
    }

    private void updateFilm() {
        String title = IO.readln("Enter the title of the Film: ");
        Film.update(em, title);
    }

    private void seriesMenu() { //throws IOException, InterruptedException {
        //clearConsole();
        System.out.println("""
            You are in the Series Menu.

            Please select an action below:

            1. Create new entry
            2. List entries
            3. List specific entry
            4. Update existing entry

            5. Exit

            """);
        switch (IO.readln()){
            case "1" -> createSeries();
            case "2" -> listSeries();
            case "3" -> listSpecificSeries();
            case "4" -> updateSeries();
            case "5" -> mainMenu(em);
            default -> invalidInput();
        }
    }

    private void createSeries() { //throws IOException, InterruptedException {
        //clearConsole();
        String title = IO.readln("Enter the title of the Series: ");
        int episodes = Integer.parseInt(IO.readln("Enter the number of episodes in the Series: "));
        int firstAired = Integer.parseInt(IO.readln("Enter the year the Series was first released: "));
        Integer lastAired =  Integer.valueOf(IO.readln("Enter the year the Series ended." +
                                                                "leave blank if not yet finished: "));
        String starActors = IO.readln("Enter the star actors of the Series: ");

        //TODO:
        //send information to create-method in Series class
    }

    private void listSeries() {
        IO.println(Series.findAll(em));
    }

    private void listSpecificSeries() {
        String title = IO.readln("Enter the title of the Series: ");
        IO.println(Series.findByName(em, title));
    }

    private void updateSeries() {
        IO.println("When prompted, enter the value you wish to update." +
            "If you don't want to change it, leave the input blank.");

        String title = IO.readln("Enter the title of the Series: ");
        Integer episodes = Integer.valueOf(IO.readln("Enter the number of episodes in the Series: "));
        Integer firstAired = Integer.valueOf(IO.readln("Enter the year the Series was first released: "));
        Integer lastAired =  Integer.valueOf(IO.readln("Enter the year the Series ended: "));
        String starActors = IO.readln("Enter the star actors of the Series: ");

        //TODO:
        //Determine which values aren't null, and update them
    }

//    private void clearConsole() throws IOException, InterruptedException {
//        if(os.contains("Windows"))
//            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
//        else
//            new ProcessBuilder("clear").inheritIO().start().waitFor();
//    }

}
