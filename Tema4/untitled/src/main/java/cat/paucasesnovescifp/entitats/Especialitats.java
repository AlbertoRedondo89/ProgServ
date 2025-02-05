package cat.paucasesnovescifp.entitats;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;

import java.util.Objects;

@Entity
@IdClass(EspecialitatPK.class)
public class Especialitats {

    @Id
    private String idCos;
    @Id
    private String idEspecialitat;
    private String descripcio;

    public Especialitats() {
    }

    public Especialitats(String idCos, String idEspecialitat, String descripcio) {
        this.idCos = idCos;
        this.idEspecialitat = idEspecialitat;
        this.descripcio = descripcio;
    }

    public String getIdCos() {
        return idCos;
    }

    public void setIdCos(String idCos) {
        this.idCos = idCos;
    }

    public String getIdEspecialitat() {
        return idEspecialitat;
    }

    public void setIdEspecialitat(String idEspecialitat) {
        this.idEspecialitat = idEspecialitat;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Especialitats that = (Especialitats) o;
        return Objects.equals(idCos, that.idCos) && Objects.equals(idEspecialitat, that.idEspecialitat) && Objects.equals(descripcio, that.descripcio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCos, idEspecialitat, descripcio);
    }

    @Override
    public String toString() {
        return "Especialitat{" +
                "idCos='" + idCos + '\'' +
                ", idEspecialitat='" + idEspecialitat + '\'' +
                ", descripcio='" + descripcio + '\'' +
                '}';
    }
}
