package dades;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Actor {
    private int id;
    private String name;
    private String birthDate;
    private String deathDate = null;
    private String nationality;

    // Formateador para las fechas, porque db40 no lleva bien LocalDate
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Actor() {
    }

    // Actor vivo
    public Actor(int id, String name, LocalDate birthDate, String nationality) {
        this.id = id;
        this.name = name;
        if (birthDate!= null) this.birthDate = birthDate.format(FORMATTER);
        this.nationality = nationality;
        this.deathDate = null;
    }

    // Actor fallecido
    public Actor(int id, String name, LocalDate birthDate, String nationality, LocalDate deathDate) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate.format(FORMATTER);
        this.deathDate = deathDate.format(FORMATTER);
        this.nationality = nationality;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return LocalDate.parse(birthDate, FORMATTER);
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate.format(FORMATTER);
    }

    public LocalDate getDeathDate() {
        return LocalDate.parse(deathDate, FORMATTER);
    }

    public void setDeathDate(LocalDate deathDate) {
        this.deathDate = deathDate.format(FORMATTER);
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Actor actor = (Actor) object;
        return id == actor.id && Objects.equals(name, actor.name) && Objects.equals(birthDate, actor.birthDate) && Objects.equals(deathDate, actor.deathDate) && Objects.equals(nationality, actor.nationality);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, birthDate, deathDate, nationality);
    }

    @Override
    public String toString() {
        return "Actor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthDate=" + birthDate +
                ", deathDate=" + deathDate +
                ", nationality='" + nationality + '\'' +
                '}';
    }
}