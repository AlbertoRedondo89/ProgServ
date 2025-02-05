package cat.paucasesnovescifp.entitats;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "illes")
@NamedQuery(name = "Illa.getIlles", query = "SELECT i FROM Illa i")
public class Illa {

    @Id
    private String idIlla;
    private String nomIlla;
    @OneToMany(mappedBy = "illa")
    private List<Localitat> localitats;

    public Illa() {
    }

    public Illa(String idIlla, String nomIlla) {
        this.idIlla = idIlla;
        this.nomIlla = nomIlla;
    }

    public String getIdIlla() {
        return idIlla;
    }

    public void setIdIlla(String idIlla) {
        this.idIlla = idIlla;
    }

    public String getNomIlla() {
        return nomIlla;
    }

    public void setNomIlla(String nomIlla) {
        this.nomIlla = nomIlla;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Illa illa = (Illa) object;
        return Objects.equals(idIlla, illa.idIlla) && Objects.equals(nomIlla, illa.nomIlla) && Objects.equals(localitats, illa.localitats);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idIlla, nomIlla, localitats);
    }

    @Override
    public String toString() {
        return "Illa{" +
                "idIlla='" + idIlla + '\'' +
                ", nomIlla='" + nomIlla + '\'' +
                '}';
    }

    public List<Localitat> getLocalitats() {
        return localitats;
    }

    public void setLocalitats(List<Localitat> localitats) {
        this.localitats = localitats;
    }
}
