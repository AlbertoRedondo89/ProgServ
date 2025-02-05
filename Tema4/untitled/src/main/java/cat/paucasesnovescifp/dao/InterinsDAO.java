package cat.paucasesnovescifp.dao;

import cat.paucasesnovescifp.entitats.Aspirant;
import cat.paucasesnovescifp.entitats.Illa;
import cat.paucasesnovescifp.entitats.Localitat;
import cat.paucasesnovescifp.entitats.Preferencies;

import java.util.List;

public interface InterinsDAO {

    Aspirant findAspirantByNif(String nif);
    boolean updateLlinatgesByNif(String nif, String llinatges);
    boolean actualitzaAspirant(Aspirant aspirantPerModificar);
    boolean creaAspirant(String nif, String nom, String llinatges, String adreca, String codiPostal, Localitat localitat);
    boolean insertaObjecte(Object object);
    boolean actualitzaObjecte(Object object);
    boolean esborraObjecte(Object object);

    List<Illa> tornaIlles();
    List<Illa> tornaIllesNamed();
    List<Localitat> tornaLocalitatsIlla(Illa illa);
    List<Localitat> tornaLocalitatsIllaSinConsulta(Illa illa);
    List<Localitat> tornaLocalitatPerIlla(Illa illa);
    List<Aspirant> tornaAspirants(int inici, int quantitat);
    List<String> getNifs(int inici, int quantitat);
    List<String> getNomComplet(int inici, int quantitat);
    List<Preferencies> getPreferencies(int inici, int quantitat);
}
