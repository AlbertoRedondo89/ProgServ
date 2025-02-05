package cat.paucasesnovescifp.entitats;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "aspirants")
@NamedQuery(name = "Aspirant.getAspirants", query = "SELECT a.nif FROM Aspirant a order by a.llinatges")
public class Aspirant {
    @Id
    private String nif;
    private String nom;
    private String llinatges;
    private String adreca;
    private String codiPostal;
    @ManyToOne
    @JoinColumn(name = "idLocalitat", referencedColumnName = "idLocalitat")
    private Localitat idLocalitat;

    public Aspirant() {
    }

    public Aspirant(String nif, String nom, String llinatges, String adreca, String codiPostal, Localitat idLocalitat) {
        this.nif = nif;
        this.nom = nom;
        setLlinatges(llinatges);
        setAdreca(adreca);
        this.codiPostal = codiPostal;
        this.idLocalitat = idLocalitat;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getLlinatges() {
        return llinatges;
    }

    public void setLlinatges(String llinatges) {
        if (llinatges == null || llinatges.isEmpty()) {
            throw new RuntimeException("llinatges is empty");
        }
        this.llinatges = llinatges;
    }

    public String getAdreca() {
        return adreca;
    }

    public void setAdreca(String adreca) {
        if (!adreca.trim().isEmpty()) this.adreca = adreca;
    }

    public String getCodiPostal() {
        return codiPostal;
    }

    public void setCodiPostal(String codiPostal) {
        this.codiPostal = codiPostal;
    }

    public Localitat getIdLocalitat() {
        return idLocalitat;
    }

    public void setIdLocalitat(Localitat idLocalitat) {
        this.idLocalitat = idLocalitat;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Aspirant aspirants = (Aspirant) object;
        return Objects.equals(nif, aspirants.nif) && Objects.equals(nom, aspirants.nom) && Objects.equals(llinatges, aspirants.llinatges) && Objects.equals(adreca, aspirants.adreca) && Objects.equals(codiPostal, aspirants.codiPostal) && Objects.equals(idLocalitat, aspirants.idLocalitat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nif, nom, llinatges, adreca, codiPostal, idLocalitat);
    }

    @Override
    public String toString() {
        return "Aspirants{" +
                "nif='" + nif + '\'' +
                ", nom='" + nom + '\'' +
                ", llinatges='" + llinatges + '\'' +
                ", adreca='" + adreca + '\'' +
                ", codiPostal='" + codiPostal + '\'' +
                ", idLocalitat='" + idLocalitat + '\'' +
                '}';
    }
}
