package cat.paucasesnovescifp.entitats;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@IdClass(PreferenciesPK.class)
public class Preferencies {
    @Id
    @ManyToOne
    @JoinColumn(name = "nif", referencedColumnName = "Nif")
    private Aspirant nif;
    @Id
    private int ordre;
    private String idCos;
    private String idEspecialitat;
    @ManyToOne
    @JoinColumn(name = "idCentre", referencedColumnName = "idCentre")
    private Centre idCentre;

    public Preferencies() {
    }

    public Preferencies(Aspirant nif, int ordre, String idCos, String idEspecialitat, Centre idCentre) {
        this.nif = nif;
        this.ordre = ordre;
        this.idCos = idCos;
        this.idEspecialitat = idEspecialitat;
        this.idCentre = idCentre;
    }

    public Aspirant getNif() {
        return nif;
    }

    public void setNif(Aspirant nif) {
        this.nif = nif;
    }

    public int getOrdre() {
        return ordre;
    }

    public void setOrdre(int ordre) {
        this.ordre = ordre;
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

    public Centre getIdCentre() {
        return idCentre;
    }

    public void setIdCentre(Centre idCentre) {
        this.idCentre = idCentre;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Preferencies that = (Preferencies) object;
        return ordre == that.ordre && Objects.equals(nif, that.nif) && Objects.equals(idCos, that.idCos) && Objects.equals(idEspecialitat, that.idEspecialitat) && Objects.equals(idCentre, that.idCentre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nif, ordre, idCos, idEspecialitat, idCentre);
    }

    @Override
    public String toString() {
        return "Preferencies{" +
                "nif='" + nif + '\'' +
                ", ordre=" + ordre +
                ", idCos='" + idCos + '\'' +
                ", idEspecialitat='" + idEspecialitat + '\'' +
                ", idCentre='" + idCentre + '\'' +
                '}';
    }
}
