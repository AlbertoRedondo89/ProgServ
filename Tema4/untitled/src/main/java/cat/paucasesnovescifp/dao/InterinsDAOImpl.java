package cat.paucasesnovescifp.dao;

import cat.paucasesnovescifp.connexio.JPAUtil;
import cat.paucasesnovescifp.entitats.Aspirant;
import cat.paucasesnovescifp.entitats.Illa;
import cat.paucasesnovescifp.entitats.Localitat;
import cat.paucasesnovescifp.entitats.Preferencies;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.lang.reflect.Type;
import java.util.List;

public class InterinsDAOImpl implements InterinsDAO {

    @Override
    public Aspirant findAspirantByNif(String nif) {
        EntityManager manager = JPAUtil.getEntityManager();
                try (manager) {
                    Aspirant aspirant = manager.find(Aspirant.class, nif);
                    return aspirant;
                } catch (Exception e) {
                    e.printStackTrace();
                }
        return null;
    }

    @Override
    public boolean updateLlinatgesByNif(String nif, String llinatges) {
        EntityManager manager = JPAUtil.getEntityManager();
        try (manager) {
            manager.getTransaction().begin();
            Aspirant aspiranteACambiar = manager.find(Aspirant.class, nif);
            aspiranteACambiar.setLlinatges(llinatges);
            manager.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean actualitzaAspirant(Aspirant aspirantPerModificar) {
        EntityManager manager = JPAUtil.getEntityManager();
        try (manager) {
            manager.getTransaction().begin();
            if (manager.contains(aspirantPerModificar)) {
                manager.merge(aspirantPerModificar);
                manager.getTransaction().commit();
                return true;
            }
            System.out.println("El aspirante con nif: " + aspirantPerModificar.getNif() + " no existe.");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean creaAspirant(String nif, String nom, String llinatges, String adreca, String codiPostal, Localitat localitat) {
        Aspirant aspirantNou = new Aspirant(nif, nom, llinatges, adreca, codiPostal, localitat);
        EntityManager manager = JPAUtil.getEntityManager();
        try (manager) {
            manager.getTransaction().begin();
            manager.persist(aspirantNou);
            manager.getTransaction().commit();
            return true;
        } catch (Exception e) {
            manager.getTransaction().rollback();
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean insertaObjecte(Object object) {
        EntityManager manager = JPAUtil.getEntityManager();
        try (manager) {
            manager.getTransaction().begin();
            manager.persist(object);
            manager.getTransaction().commit();
            return true;
        } catch (Exception e) {
            manager.getTransaction().rollback();
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean actualitzaObjecte(Object object) {
        EntityManager manager = JPAUtil.getEntityManager();
        try (manager) {
            manager.getTransaction().begin();
            manager.merge(object);
            manager.getTransaction().commit();
            return true;
        } catch (Exception e) {
            manager.getTransaction().rollback();
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean esborraObjecte(Object object) {
        EntityManager manager = JPAUtil.getEntityManager();
        try (manager) {
            manager.getTransaction().begin();
            Object objetoAEliminar = manager.merge(object);
            manager.remove(manager.contains(objetoAEliminar) ? objetoAEliminar : manager.merge(objetoAEliminar));
            manager.getTransaction().commit();
            return true;
        } catch (Exception e) {
            manager.getTransaction().rollback();
            e.printStackTrace();
        }
        return false;
    }

    public List<Illa> tornaIlles() {
        EntityManager manager = JPAUtil.getEntityManager();
        TypedQuery<Illa> queryIlles = manager.createQuery("SELECT i FROM Illa i", Illa.class);
        return queryIlles.getResultList();
    }

    public List<Illa> tornaIllesNamed() {
        EntityManager manager = JPAUtil.getEntityManager();
        TypedQuery<Illa> queryIlles = manager.createNamedQuery("Illa.getIlles", Illa.class);

        return queryIlles.getResultList();
    }

    public List<Localitat> tornaLocalitatsIlla(Illa illa) {
        EntityManager manager = JPAUtil.getEntityManager();
        TypedQuery<Localitat> queryLocalitats = manager.createQuery("SELECT i FROM Localitat i where i.illa = ?1", Localitat.class);
        queryLocalitats.setParameter(1, illa);

        return queryLocalitats.getResultList();
    }

    public List<Localitat> tornaLocalitatsIllaSinConsulta(Illa illa) {
        return illa.getLocalitats();
    }

    public List<Localitat> tornaLocalitatPerIlla(Illa illa) {
        EntityManager manager = JPAUtil.getEntityManager();
        TypedQuery<Localitat> queryLocalitats = manager.createNamedQuery("Localitat.locsPerIlla", Localitat.class);
        queryLocalitats.setParameter("idIlla", illa);
        return queryLocalitats.getResultList();
    }

    public List<Aspirant> tornaAspirants(int inici, int quantitat) {
       EntityManager manager = JPAUtil.getEntityManager();
       TypedQuery<Aspirant> queryAspirants = manager.createQuery("SELECT i FROM Aspirant i order by i.llinatges", Aspirant.class);
       queryAspirants.setFirstResult(inici);
       queryAspirants.setMaxResults(quantitat);
       return queryAspirants.getResultList();
    }

    public List<String> getNifs(int inici, int quantitat) {
        List<String> nifs = List.of();
        EntityManager manager = JPAUtil.getEntityManager();

        TypedQuery<String> queryAspirants = manager.createNamedQuery("Aspirant.getAspirants", String.class);
        queryAspirants.setFirstResult(inici);
        queryAspirants.setMaxResults(quantitat);
        nifs = queryAspirants.getResultList();
        return nifs;
    }

    public List<String> getNomComplet(int inici, int quantitat) {
        List<String> nomComplet = List.of();

        return nomComplet;
    }

    public List<Preferencies> getPreferencies(int inici, int quantitat) {
        List<Preferencies> preferencias = List.of();

        return preferencias;
    }

}

/*
        EntityManager manager = JPAUtil.getEntityManager();
        try (manager) {

        } catch (Exception e) {
            e.printStackTrace();
        }
*/
