import dades.Actor;
import dades.Film;
import gestor.BaseDades;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        BaseDades bd = new BaseDades();

        // 1 Inicialitzar la base de dades db4o com a mínim amb 10 actors i 5 pel·lícules (han de ser dades realistes).
        bd.creaBaseDades();

        // 2 Recuperar totes les dades de la base de dades.
        System.out.println("\n------------------------- EJERCICIO 2 ");
        bd.showAllData();

        // 3 Recuperar un film a partir del títol.
        System.out.println("\n------------------------- EJERCICIO 3 ");
        Film filmBuscado = bd.findMovieFromTitle("Titanic");
        System.out.println(filmBuscado);

        // 4 Recuperar el repartiment d’un film a partir de la seva referència.
        ArrayList<Actor> actoresPerFilmRef = bd.findActorsFromRef("F001");
        System.out.println("\n------------------------- EJERCICIO 4 ");
        System.out.println("Actores de la película: ");
        actoresPerFilmRef.forEach(System.out::println);

        // 5 Recuperar els films a partir d’un gènere donat.
        ArrayList<Film> moviesPerGenre = bd.findFilmsByGenre("Acción");
        System.out.println("\n------------------------- EJERCICIO 5 ");
        System.out.println("Films del génere: 'Acción'");
        for (Film film : moviesPerGenre) {
            System.out.println(film.getTitle());
        }

        // 6 Afegir la data de defunció d’un actor a partir del seu identificador.
        System.out.println("\n------------------------- EJERCICIO 6");
        LocalDate defuncion = LocalDate.now();
        Actor actorVivo = bd.getActor(1);
        System.out.println("... Buscando actor al que matar... ");
        System.out.println(actorVivo);
        bd.setActorDeathDate(1, defuncion);
        Actor actorMuerto = bd.getActor(1);
        System.out.println("... actor asesinado con éxito ... ");
        System.out.println(actorMuerto);


        // 7 Afegir un actor al repartiment d’un film. Cal veure si l’actor ja existeix a la base de dades per no duplicar-lo.
        System.out.println("\n------------------------- EJERCICIO 7");
        Actor actorNuevo = new Actor(21, "Charlize Theron", LocalDate.of(1975, 4, 4), "SudAfrica");
        bd.addActorToMovie(actorNuevo, "F001");
        Film filmConNuevoActor = bd.getFilm("F001");
        System.out.println(filmConNuevoActor);

        // 8 Eliminar un actor del repartiment d’una pel·lícula a partir de l’identificador i la referència d’ambdós.
        System.out.println("\n------------------------- EJERCICIO 8");
        boolean eliminado = bd.deleteActorFromMovie(21, "F001");
        System.out.println(eliminado);

        // 9 Eliminar un actor a partir del seu identificador. Cal eliminar també totes les pel·lícules on actua.
        System.out.println("\n------------------------- EJERCICIO 9");
        bd.deleteActorWithmovies(3);

        //bd.showAllData();


    }
}
