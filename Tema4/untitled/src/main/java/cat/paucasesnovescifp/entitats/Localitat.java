package cat.paucasesnovescifp.entitats;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "localitats")
@NamedQuery(name = "Localitat.locsPerIlla", query = "SELECT l FROM Localitat l where l.illa = :idIlla")
public class Localitat {
    @Id
    private String idLocalitat;
    @ManyToOne
    @JoinColumn(name = "idIlla", referencedColumnName = "idIlla")
    private Illa illa;
    private String nomLocalitat;

    public Localitat() {
    }

    public Localitat(String idLocalitat, Illa idIlla, String nomLocalitat) {
        this.idLocalitat = idLocalitat;
        this.illa = idIlla;
        this.nomLocalitat = nomLocalitat;
    }

    public String getIdLocalitat() {
        return idLocalitat;
    }

    public void setIdLocalitat(String idLocalitat) {
        this.idLocalitat = idLocalitat;
    }

    public Illa getIdIlla() {
        return illa;
    }

    public void setIdIlla(Illa idIlla) {
        this.illa = idIlla;
    }

    public String getNomLocalitat() {
        return nomLocalitat;
    }

    public void setNomLocalitat(String nomLocalitat) {
        this.nomLocalitat = nomLocalitat;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Localitat that = (Localitat) object;
        return Objects.equals(idLocalitat, that.idLocalitat) && Objects.equals(illa, that.illa) && Objects.equals(nomLocalitat, that.nomLocalitat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idLocalitat, illa, nomLocalitat);
    }

    @Override
    public String toString() {
        return "Localitats{" +
                "idLocalitat='" + idLocalitat + '\'' +
                ", idIlla='" + illa + '\'' +
                ", nomLocalitat='" + nomLocalitat + '\'' +
                '}';
    }
}
