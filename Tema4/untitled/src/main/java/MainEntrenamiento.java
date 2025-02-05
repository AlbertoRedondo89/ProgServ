import cat.paucasesnovescifp.dao.InterinsDAO;
import cat.paucasesnovescifp.dao.InterinsDAOImpl;
import cat.paucasesnovescifp.entitats.Aspirant;
import cat.paucasesnovescifp.entitats.Illa;
import cat.paucasesnovescifp.entitats.Localitat;

import java.util.List;

public class MainEntrenamiento {
    public static void main(String[] args) {
        InterinsDAO dao = new InterinsDAOImpl();

        try {
// EJERCICIO 1
            // Un mètode per recuperar un aspirant.
            /*
            Aspirant aspirant = dao.findAspirantByNif("");
            System.out.println(aspirant);
             */

// EJERCICIO 2
            // Un mètode per modificar els llinatges de l'aspirant. A la base de dades ni llinatges ni nom poden ser
            // nuls, per tant modifica la classe Aspirant de manera que no permeti assignar valors nuls, cadenes
            // buides o formades per espais.
            /*
            boolean hecho = dao.updateLlinatgesByNif("", "Nuevo Apellido");
            System.out.println(hecho);
            */


// EJERCICIO 3
            // Un mètode per actualitzar les dades d'un aspirant a partir de l'objecte Aspirant rebut.
            /*
            Aspirant aspirant2 = dao.findAspirantByNif("");
            aspirant2.setLlinatges("Li");
            boolean aspiranteActualizado = dao.actualitzaAspirant(aspirant2);
            */

// EJERCICIO 4
            // Un mètode per crear un nou aspirant.


// EJERCICIO 5
            // Un mètode per inserir qualsevol objecte a la base de dades. Prova'l afegint una illa.


// EJERCICIO 6
            // Un mètode que actualitza qualsevol objecte que li passem a la base de dades.


// EJERCICIO 7
            // Un mètode que esborra qualsevol objecte que li passem a la base de dades.


            // SEGUNDA PARTE

// EJERCICIO 1
            //  Torna una llista amb totes totes les illes de la base de dades. Crea una consulta dinàmica per fer-ho.
            /*
            List<Illa> illes = dao.tornaIlles();
            illes.forEach(System.out::println);
             */

// EJERCICIO 2
            // Repeteix el mètode anterior utilitzant una Named Query.

            //List<Illa> illes2 = dao.tornaIllesNamed();
            //illes2.forEach(System.out::println);

// EJERCICIO 3
            // crea una consulta dinàmica que torni totes les localitats d'una illa determinada.
            /*
            List<Localitat> localitatsPerIlla = dao.tornaLocalitatsIlla(illes2.get(2));
            localitatsPerIlla.forEach(System.out::println);
             */

// EJERCICIO 4
            // Repeteix l'exercici anterior sense utilitzar consultes?
            //List<Localitat> localitatsSinConsulta = dao.tornaLocalitatsIllaSinConsulta(illes2.get(2));
            //localitatsSinConsulta.forEach(System.out::println);


// EJERCICIO 5
            // Crea una namedQuery a la classe que toqui per tornar totes les localitats d'una determinada illa
            // i implementa un mètode per utilitzar-la.
            //List<Localitat> localitatsPerIlla = dao.tornaLocalitatPerIlla(illes2.get(1));
            //localitatsPerIlla.forEach(System.out::println);


// EJERCICIO 6
            // torna tants aspirants (ordenats per llinatges) com indiqui quantitat començant per la posició que indica inici.
            List<Aspirant> aspirantesLimitados = dao.tornaAspirants(2, 6);
            aspirantesLimitados.forEach(System.out::println);


// EJERCICIO 7
            //  Torna una llista que conté només els nifs dels aspirants (ordenats per llinatges)
            //  de la base de dades. Utilitza una NamedQuery.
            List<String> nifses = dao.getNifs(2, 6);
            nifses.forEach(System.out::println);


// EJERCICIO 8
            // Torna només el nom i els llinatges dels aspirants (ordenats per llinatges i nom). No pots utilitzar una TypedQuery.


// EJERCICIO 9
            // Torna les preferències de la base de dades ordenades per id de centre.



        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
