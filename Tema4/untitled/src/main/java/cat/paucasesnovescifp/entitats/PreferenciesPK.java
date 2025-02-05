package cat.paucasesnovescifp.entitats;

import java.util.Objects;

public class PreferenciesPK {
    private String nif;
    private int ordre;

    public PreferenciesPK() {
    }

    public PreferenciesPK(String nif, int ordre) {
        this.nif = nif;
        this.ordre = ordre;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public int getOrdre() {
        return ordre;
    }

    public void setOrdre(int ordre) {
        this.ordre = ordre;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        PreferenciesPK that = (PreferenciesPK) object;
        return ordre == that.ordre && Objects.equals(nif, that.nif);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nif, ordre);
    }

    @Override
    public String toString() {
        return "PreferenciesPK{" +
                "nif='" + nif + '\'' +
                ", ordre=" + ordre +
                '}';
    }
}
