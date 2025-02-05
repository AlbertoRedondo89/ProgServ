package cat.paucasesnovescifp.entitats;

import java.util.Objects;

public class EspecialitatPK {
    private String idCos;
    private String idEspecialitat;

    public EspecialitatPK() {
    }

    public EspecialitatPK(String idCos, String idEspecialitat) {
        this.idCos = idCos;
        this.idEspecialitat = idEspecialitat;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EspecialitatPK that = (EspecialitatPK) o;
        return Objects.equals(idCos, that.idCos) && Objects.equals(idEspecialitat, that.idEspecialitat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCos, idEspecialitat);
    }

    @Override
    public String toString() {
        return "EspecialitatPK{" +
                "idCos='" + idCos + '\'' +
                ", idEspecialitat='" + idEspecialitat + '\'' +
                '}';
    }
}
