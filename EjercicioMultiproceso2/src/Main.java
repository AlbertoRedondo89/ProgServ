import java.util.Scanner;

public class Main {

    public static void lanzador(String n) {
        String ruta = "C:\\Users\\alber\\Desktop\\GIT\\Programación\\ProgServ\\EjercicioMultiproceso2_ModificaString\\src\\ModificaString.java";
        try {
            ProcessBuilder pb = new ProcessBuilder("java", ruta, n);
            pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
            pb.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Dime algo, pichón");
        String input = "";
        while (true)  {
            input = scanner.nextLine();
            try {
                if (input.equals("exit")) break;
                System.out.println("El padre dice: ");
                Main.lanzador(input);
            } catch (NumberFormatException nfe) {
                System.err.println("Fatal");
            }

        }
        scanner.close();
        System.out.println("Se acabó la fiesta");

    }
}