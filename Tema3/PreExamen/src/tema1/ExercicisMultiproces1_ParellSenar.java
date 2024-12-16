package tema1;

// ExercicisMultiproces1_ParellSenar.java
public class ExercicisMultiproces1_ParellSenar {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("S'ha d'introduir un sol número com a argument.");
            return;
        }

        try {
            int numero = Integer.parseInt(args[0]);
            if (numero < 0) {
                System.out.println("El número ha de ser positiu.");
                return;
            }

            if (numero % 2 == 0) {
                System.out.println("Parell");
            } else {
                System.out.println("Senar");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: L'argument ha de ser un número enter positiu.");
        }
    }
}
