package cat.paucasesnovescifp.entitats;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "cossos")
@NamedQuery(name = "Cos.findAll", query = "select c from Cos c")
public class Cos {
    @Id
    private String idCos;
    private String descripcio;

    public Cos() {
    }

    public Cos(String idCos, String descripcio) {
        this.idCos = idCos;
        this.descripcio = descripcio;
    }

    public String getIdCos() {
        return idCos;
    }

    public void setIdCos(String idCos) {
        this.idCos = idCos;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Cos cossos = (Cos) object;
        return Objects.equals(idCos, cossos.idCos) && Objects.equals(descripcio, cossos.descripcio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCos, descripcio);
    }

    @Override
    public String toString() {
        return "Cos{" +
                "idCos='" + idCos + '\'' +
                ", descripcio='" + descripcio + '\'' +
                '}';
    }
}
