package dades;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Film {
    private String reference;
    private String title;
    private int duration;
    private String director;
    private String plot;
    private ArrayList<String> genres;
    private double rating;
    private ArrayList<Actor> cast;

    public Film() {
    }

    public Film(String reference, String title, int duration, String director, String plot, ArrayList<String> genres, double rating, ArrayList<Actor> cast) {
        this.reference = reference;
        this.title = title;
        this.duration = duration;
        this.director = director;
        this.plot = plot;
        this.genres = genres;
        this.rating = rating;
        this.cast = cast;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public ArrayList<String> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<String> genres) {
        this.genres = genres;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public ArrayList<Actor> getCast() {
        return cast;
    }

    public void setCast(ArrayList<Actor> cast) {
        this.cast = cast;
    }

    public void addActor(Actor actor) {
        if (this.cast == null) {
            this.cast = new ArrayList<>();
        }
        this.cast.add(actor);
    }
    public void deleteActor(Actor actor){
        this.cast.remove(actor);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Film film = (Film) object;
        return duration == film.duration && Double.compare(rating, film.rating) == 0 && Objects.equals(reference, film.reference) && Objects.equals(title, film.title) && Objects.equals(director, film.director) && Objects.equals(plot, film.plot) && Objects.equals(genres, film.genres) && Objects.equals(cast, film.cast);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reference, title, duration, director, plot, genres, rating, cast);
    }

    @Override
    public String toString() {
        return "Film{" +
                "reference='" + reference + '\'' +
                ", title='" + title + '\'' +
                ", duration=" + duration +
                ", director='" + director + '\'' +
                ", plot='" + plot + '\'' +
                ", genres=" + genres +
                ", rating=" + rating +
                ", cast=" + cast +
                '}';
    }
}