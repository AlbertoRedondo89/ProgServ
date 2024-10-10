import java.util.Arrays;
import java.util.Scanner;

public class Senar {

    public static void esSenar() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Introduce un número!");
        String inutStr = scanner.nextLine();
        int n = 0;

        try {
            n = Integer.parseInt(inutStr);
        }catch (NumberFormatException nfe) {
            System.out.println("Error tremebundo");
        }

    }

    public static String parOImpar(int num) {
        String respuesta = "es IMPAR";
        if (num % 2 == 0) respuesta = " es PAR";
        System.out.println(respuesta);
        return respuesta;
    }

    public static void main(String[] args) {
        Senar.parOImpar(Integer.parseInt(args[0]));
    }

}
