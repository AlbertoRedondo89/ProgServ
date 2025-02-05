package cat.paucasesnovescifp.entitats;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "centres")
public class Centre {
    @Id
    private String idCentre;
    private String nomCentre;
    @ManyToOne
    @JoinColumn(name = "idLocalitat", referencedColumnName = "idLocalitat")
    private Localitat localitat;

    public Centre() {

    }

    public Centre(String idCentre, String nomCentre, Localitat idLocalitat) {
        this.idCentre = idCentre;
        this.nomCentre = nomCentre;
        this.localitat = idLocalitat;
    }

    public String getIdCentre() {
        return idCentre;
    }

    public void setIdCentre(String idCentre) {
        this.idCentre = idCentre;
    }

    public String getNomCentre() {
        return nomCentre;
    }

    public void setNomCentre(String nomCentre) {
        this.nomCentre = nomCentre;
    }

    public Localitat getLocalitat() {
        return localitat;
    }

    public void setLocalitat(Localitat idLocalitat) {
        this.localitat = idLocalitat;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Centre centres = (Centre) object;
        return Objects.equals(idCentre, centres.idCentre) && Objects.equals(nomCentre, centres.nomCentre) && Objects.equals(localitat, centres.localitat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCentre, nomCentre, localitat);
    }

    @Override
    public String toString() {
        return "Centres{" +
                "idCentre='" + idCentre + '\'' +
                ", nomCentre='" + nomCentre + '\'' +
                ", idLocalitat='" + localitat + '\'' +
                '}';
    }
}
