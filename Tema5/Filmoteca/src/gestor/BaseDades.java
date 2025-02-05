package gestor;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.ext.DatabaseClosedException;
import com.db4o.ext.DatabaseReadOnlyException;
import com.db4o.ext.Db4oIOException;
import com.db4o.query.Predicate;
import dades.*;

import javax.lang.model.type.ArrayType;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BaseDades {
    private static final String BD = "provesBD.db4o";

    // EJERCICIO 1
    public void creaBaseDades() {
        ObjectContainer oc = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), BD);

        Actor a1 = new Actor(1, "Robert De Niro", LocalDate.of(1943, 8, 17), "EUA");
        Actor a2 = new Actor(2, "Meryl Streep", LocalDate.of(1949, 6, 22), "EUA");
        Actor a3 = new Actor(3, "Leonardo DiCaprio", LocalDate.of(1974, 11, 11), "EUA");
        Actor a4 = new Actor(4, "Scarlett Johansson", LocalDate.of(1984, 11, 22), "EUA");
        Actor a5 = new Actor(5, "Tom Hanks", LocalDate.of(1956, 7, 9), "EUA");
        Actor a6 = new Actor(6, "Natalie Portman", LocalDate.of(1981, 6, 9), "Israel");
        Actor a7 = new Actor(7, "Joaquin Phoenix", LocalDate.of(1974, 10, 28), "EUA");
        Actor a8 = new Actor(8, "Morgan Freeman", LocalDate.of(1937, 6, 1), "EUA");
        Actor a9 = new Actor(9, "Angelina Jolie", LocalDate.of(1975, 6, 4), "EUA");
        Actor a10 = new Actor(10, "Denzel Washington", LocalDate.of(1954, 12, 28), "EUA");
        Actor a11 = new Actor(11, "Brad Pitt", LocalDate.of(1963, 12, 18), "EUA");
        Actor a12 = new Actor(12, "Anne Hathaway", LocalDate.of(1982, 11, 12), "EUA");
        Actor a13 = new Actor(13, "Al Pacino", LocalDate.of(1940, 4, 25), "EUA");
        Actor a14 = new Actor(14, "Emma Stone", LocalDate.of(1988, 11, 6), "EUA");
        Actor a15 = new Actor(15, "Keanu Reeves", LocalDate.of(1964, 9, 2), "Canadà");
        Actor a16 = new Actor(16, "Chris Hemsworth", LocalDate.of(1983, 8, 11), "Austràlia");
        Actor a17 = new Actor(17, "Cate Blanchett", LocalDate.of(1969, 5, 14), "Austràlia");
        Actor a18 = new Actor(18, "Hugh Jackman", LocalDate.of(1968, 10, 12), "Austràlia");
        Actor a19 = new Actor(19, "Ryan Reynolds", LocalDate.of(1976, 10, 23), "Canadà");
        Actor a20 = new Actor(20, "Margot Robbie", LocalDate.of(1990, 7, 2), "Austràlia");


        ArrayList<Actor> cast1 = new ArrayList<>(List.of(a13, a1));
        ArrayList<Actor> cast2 = new ArrayList<>(List.of(a3, a12));
        ArrayList<Actor> cast3 = new ArrayList<>(List.of(a5));
        ArrayList<Actor> cast4 = new ArrayList<>(List.of(a7));
        ArrayList<Actor> cast5 = new ArrayList<>(List.of(a7));
        ArrayList<Actor> cast6 = new ArrayList<>(List.of(a3));
        ArrayList<Actor> cast7 = new ArrayList<>(List.of(a16, a19));
        ArrayList<Actor> cast8 = new ArrayList<>(List.of(a15));
        ArrayList<Actor> cast9 = new ArrayList<>(List.of(a14, a3));
        ArrayList<Actor> cast10 = new ArrayList<>(List.of(a7));
        ArrayList<Actor> cast11 = new ArrayList<>(List.of(a19));
        ArrayList<Actor> cast12 = new ArrayList<>(List.of(a3, a12));
        ArrayList<Actor> cast13 = new ArrayList<>(List.of(a17));
        ArrayList<Actor> cast14 = new ArrayList<>(List.of(a3));
        ArrayList<Actor> cast15 = new ArrayList<>(List.of(a20));
        ArrayList<Actor> cast16 = new ArrayList<>(List.of(a18));
        ArrayList<Actor> cast17 = new ArrayList<>(List.of(a8));
        ArrayList<Actor> cast18 = new ArrayList<>(List.of(a12));
        ArrayList<Actor> cast19 = new ArrayList<>(List.of(a12));
        ArrayList<Actor> cast20 = new ArrayList<>(List.of(a17));

        Film f1 = new Film("F001", "El Padrino", 175, "Francis Ford Coppola", "Historia de la mafia", new ArrayList<>(List.of("Drama", "Crimen")), 9.2, cast1);
        Film f2 = new Film("F002", "Titanic", 195, "James Cameron", "Historia de amor en el Titanic", new ArrayList<>(List.of("Drama", "Romance")), 8.8, cast2);
        Film f3 = new Film("F003", "Forrest Gump", 142, "Robert Zemeckis", "Un hombre con una vida extraordinaria", new ArrayList<>(List.of("Drama")), 8.8, cast3);
        Film f4 = new Film("F004", "Gladiator", 155, "Ridley Scott", "Lucha por el honor en la Antigua Roma", new ArrayList<>(List.of("Acción", "Drama")), 8.5, cast4);
        Film f5 = new Film("F005", "El Caballero Oscuro", 152, "Christopher Nolan", "Batman contra el Joker", new ArrayList<>(List.of("Acción", "Crimen")), 9.0, cast5);
        Film f6 = new Film("F006", "Origen", 148, "Christopher Nolan", "Viaje entre sueños", new ArrayList<>(List.of("Ciencia ficción", "Acción")), 8.8, cast6);
        Film f7 = new Film("F007", "Los Vengadores", 143, "Joss Whedon", "Superhéroes salvando el mundo", new ArrayList<>(List.of("Acción", "Aventura")), 8.0, cast7);
        Film f8 = new Film("F008", "Matrix", 136, "The Wachowskis", "Realidad y simulación", new ArrayList<>(List.of("Ciencia ficción", "Acción")), 8.7, cast8);
        Film f9 = new Film("F009", "La La Land", 128, "Damien Chazelle", "Historia de amor y sueños", new ArrayList<>(List.of("Musical", "Romance")), 8.0, cast9);
        Film f10 = new Film("F010", "Joker", 122, "Todd Phillips", "Origen del Joker", new ArrayList<>(List.of("Drama", "Crimen")), 8.5, cast10);
        Film f11 = new Film("F011", "Deadpool", 108, "Tim Miller", "Un antihéroe irreverente", new ArrayList<>(List.of("Acción", "Comedia")), 8.0, cast11);
        Film f12 = new Film("F012", "Interstellar", 169, "Christopher Nolan", "Viaje espacial más allá del tiempo", new ArrayList<>(List.of("Ciencia ficción", "Drama")), 8.6, cast12);
        Film f13 = new Film("F013", "El Señor de los Anillos", 201, "Peter Jackson", "La lucha por la Tierra Media", new ArrayList<>(List.of("Fantasía", "Aventura")), 9.0, cast13);
        Film f14 = new Film("F014", "El Gran Gatsby", 143, "Baz Luhrmann", "Fiestas y drama en los años 20", new ArrayList<>(List.of("Drama", "Romance")), 7.2, cast14);
        Film f15 = new Film("F015", "Mujer Maravilla", 141, "Patty Jenkins", "La heroína amazona", new ArrayList<>(List.of("Acción", "Fantasía")), 7.4, cast15);
        Film f16 = new Film("F016", "Logan", 137, "James Mangold", "El final de Wolverine", new ArrayList<>(List.of("Acción", "Drama")), 8.1, cast16);
        Film f17 = new Film("F017", "Piratas del Caribe", 143, "Gore Verbinski", "Piratas, tesoros y aventuras", new ArrayList<>(List.of("Acción", "Aventura")), 7.9, cast17);
        Film f18 = new Film("F018", "Harry Potter y la piedra filosofal", 152, "Chris Columbus", "El inicio de la saga", new ArrayList<>(List.of("Fantasía", "Aventura")), 7.6, cast18);
        Film f19 = new Film("F019", "El Diario de Noa", 123, "Nick Cassavetes", "Un romance inolvidable", new ArrayList<>(List.of("Drama", "Romance")), 7.9, cast19);
        Film f20 = new Film("F020", "Bohemian Rhapsody", 134, "Bryan Singer", "Historia de Freddie Mercury", new ArrayList<>(List.of("Drama", "Música")), 8.0, cast20);

        try {
            // Insertar Actores en la base de datos
            oc.store(a1);
            oc.store(a2);
            oc.store(a3);
            oc.store(a4);
            oc.store(a5);
            oc.store(a6);
            oc.store(a7);
            oc.store(a8);
            oc.store(a9);
            oc.store(a10);
            oc.store(a11);
            oc.store(a12);
            oc.store(a13);
            oc.store(a14);
            oc.store(a15);
            oc.store(a16);
            oc.store(a17);
            oc.store(a18);
            oc.store(a19);
            oc.store(a20);

            // Insertar Películas en la base de datos
            oc.store(f1);
            oc.store(f2);
            oc.store(f3);
            oc.store(f4);
            oc.store(f5);
            oc.store(f6);
            oc.store(f7);
            oc.store(f8);
            oc.store(f9);
            oc.store(f10);
            oc.store(f11);
            oc.store(f12);
            oc.store(f13);
            oc.store(f14);
            oc.store(f15);
            oc.store(f16);
            oc.store(f17);
            oc.store(f18);
            oc.store(f19);
            oc.store(f20);

            System.out.println("Actores y películas almacenados correctamente en la base de datos.");

        } finally {
            // Cerrar la base de datos
            oc.close();
        }

    }

    // EJERCICIO 2
    public void showAllData() {
        ObjectContainer oc = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), BD);

        // Metodo para mostrar todos los datos. He hecho busquedas de Film Y Actor en lugar de una sola busquedac de Object para evitar que muestre
        // los arrayList, que los cuenta como objeto de la BD y la información queda confusa.

        ObjectSet<Film> movies = oc.queryByExample(Film.class);
        System.out.println("--------------- Lista de Películas ----------------");
        movies.forEach(System.out::println);

        ObjectSet<Actor> actors = oc.queryByExample(Actor.class);
        System.out.println("--------------- Lista de Actores ----------------");
        actors.forEach(System.out::println);

        oc.close();
    }

    // EJERCICIO 3
    public Film findMovieFromTitle(String title) {
        Film movie;
        ObjectContainer oc = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), BD);

        ObjectSet<Film> movies = oc.query(new Predicate<Film>() {
            @Override
            public boolean match(Film film) {
                return film.getTitle().equals(title);
            }
        });
        movie = movies.next();
        oc.close();
        return movie;
    }

    // EJERCICIO 4
    public ArrayList<Actor> findActorsFromRef(String ref) {
        ObjectContainer oc = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), BD);
        ObjectSet<Film> movies = oc.query(new Predicate<Film>() {
            @Override
            public boolean match(Film film) {
                return film.getReference().equals(ref);
            }
        });
        Film f = movies.next();
        oc.close();
        return f.getCast();
    }

    // EJERCICIO 5
    public ArrayList<Film> findFilmsByGenre(String genre) {
        ArrayList<Film> films = new ArrayList<>();
        ObjectContainer oc = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), BD);
        ObjectSet<Film> movies = oc.query(new Predicate<Film>() {
            @Override
            public boolean match(Film film) {
                return film.getGenres().contains(genre);
            }
        });
        films.addAll(movies);
        oc.close();
        return films;
    }

    // EJERCICIO 6
    public void setActorDeathDate(int id, LocalDate deathDate) {
        ObjectContainer oc = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), BD);
        ObjectSet<Actor> actors = oc.queryByExample(new Actor(id, null, null, null));

        Actor actorAModificar = actors.next();
        actorAModificar.setDeathDate(deathDate);
        oc.store(actorAModificar);

        oc.close();
    }
    // EJERCICIO 7
    public void addActorToMovie(Actor actor, String movie) {
        Actor actorTemp;
        Film f;
        ObjectContainer oc = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), BD);

        try {
            ObjectSet<Film> movies = oc.query(new Predicate<Film>() {
                @Override
                public boolean match(Film film) {
                    return film.getReference().equals(movie);
                }
            });
            if (movies.isEmpty()) throw new IllegalArgumentException("La película no existe");
            f = movies.next();

            ObjectSet<Actor> actors = oc.queryByExample(new Actor(actor.getId(), null, null, null));
            if (!actors.isEmpty()) {
                actorTemp = actors.next();
                System.out.println("Actor ya existente en la Base de datos");
            } else {
                actorTemp = actor;
                oc.store(actorTemp);
                System.out.println("Actor añadido a la Base de datos");
            }

            if (!f.getCast().contains(actorTemp)) {
                f.addActor(actorTemp);
                //Por como maneja los datos db4o, parece que debe guardarse explicitamente el arraylist, ya que es un objeto y no sev guarda de forma automática al guardar Film.
                oc.store(f.getCast());
                oc.store(f);
                oc.commit();
                System.out.println("Actor " + actorTemp.getName() + " añadido correctamente al casting de la película " + f.getTitle());
            } else System.out.println("Actor " + actorTemp.getName() + " ya pertenece al reparto de la pelicula " + f.getTitle());
        } catch (Db4oIOException e) {
            throw new RuntimeException(e);
        }  finally {
            oc.close();
        }

    }

    // EJERCICIO 8
    public boolean deleteActorFromMovie(int actor, String movie) {
        Actor actorTemp = null;
        Film f;
        ObjectContainer oc = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), BD);

        try {
            ObjectSet<Film> movies = oc.query(new Predicate<Film>() {
                @Override
                public boolean match(Film film) {
                    return film.getReference().equals(movie);
                }
            });
            if (movie.isEmpty()) throw new IllegalArgumentException("La película no existe en la base de datos");
            f = movies.next();

            ObjectSet<Actor> actors = oc.queryByExample(new Actor(actor, null, null, null));
            if (!actors.isEmpty()) {
                actorTemp = actors.next();
            } else {
                System.out.println("El actor no existe en la Base de datos");
            }

            if (!f.getCast().contains(actorTemp)) {
                f.addActor(actorTemp);
                oc.store(f);
                oc.commit();
                System.out.println("Actor " + actorTemp.getName() + " no existe en el casting de la película " + f.getTitle());
            } else {
                f.deleteActor(actorTemp);
                oc.store(f.getCast());
                oc.store(f);
                oc.commit();
                System.out.println("Actor " + actorTemp.getName() + " eliminado del reparto de la pelicula " + f.getTitle());
                return true;
            }
        } catch (Db4oIOException e) {
            throw new RuntimeException(e);
        }  finally {
            oc.close();
        }
        return false;
    }

    // EJERCICIO 9
    public void deleteActorWithmovies(int actor) {
        Actor actorAEliminar;
        ObjectContainer oc = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), BD);

        try {
            ObjectSet<Actor> actors = oc.queryByExample(new Actor(actor, null, null, null));
            if (actors.isEmpty()) {
                System.out.println("El actor no existe en la Base de datos");
                return;
            }
            actorAEliminar = actors.next();
            
            ObjectSet<Film> films = oc.queryByExample(Film.class);
            if (!films.isEmpty()) {
                for (Film film : films) {
                    if (film.getCast().contains(actorAEliminar)) {
                        oc.delete(film);
                        System.out.println(film.getTitle() + " eliminada.");
                    }
                }
            }
            oc.delete(actorAEliminar);
            System.out.println("Actor " + actorAEliminar.getName() + " eliminado.");
            oc.commit();
        } catch (Db4oIOException e) {
            throw new RuntimeException(e);
        } finally {
            oc.close();
        }
    }

    // AUXILIARES
    public Actor getActor(int id) {
        ObjectContainer oc = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), BD);
        ObjectSet<Actor> actors = oc.queryByExample(new Actor(id, null, null, null));
        Actor actorbuscado = actors.next();
        oc.close();
        return actorbuscado;
    }
    public Film getFilm(String ref) {
        ObjectContainer oc = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), BD);
        ObjectSet<Film> movies = oc.query(new Predicate<Film>() {
            @Override
            public boolean match(Film film) {
                return film.getReference().equals(ref);
            }
        });
        Film filmbuscado = movies.next();
        oc.close();
        return filmbuscado;
    }
}