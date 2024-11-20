package tools;

import java.util.Scanner;

public class Tools {

    public static int pideNumero(String frase1, String fraseMinimo, String fraseMaximo, int max, int min) {
        Scanner get = new Scanner(System.in);
        int numeroADevolver = 0;
        boolean valido = false;
        int contador = 0;
        System.out.println(frase1);
        while (!valido) {
            if (!get.hasNextInt()) { // Comprueba si hay un número válido antes de leerlo
                System.out.println("Eso no es un número válido, prueba otra vez.");
                get.next(); // Consumir la entrada inválida
                continue;
            }
            numeroADevolver = get.nextInt();
            get.nextLine();
            if (numeroADevolver <= max && numeroADevolver >= min) valido = true;
            else {
                if (contador > 9) {
                    fraseMaximo = "Yo paso ya...";
                    fraseMinimo= "Lo que tú digas...";
                }
                if (numeroADevolver < min) {
                    System.out.println(fraseMinimo);
                } else {
                    System.out.println(fraseMaximo);
                }
                contador++;
            }
        }
        return numeroADevolver;
    }

}
