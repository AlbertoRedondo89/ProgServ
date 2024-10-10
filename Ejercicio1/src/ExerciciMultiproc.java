import java.util.Scanner;

public class ExerciciMultiproc {

    public void lanzador(int n) {
        String ruta = "src/Exercici.java";
        try {
            ProcessBuilder pb = new ProcessBuilder("java", ruta, String.valueOf(n));
            pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
            pb.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Dime un numero, pichón");
        String input = "";
        int n = 0;
        while (true)  {
            ExerciciMultiproc proc = new ExerciciMultiproc();
            input = scanner.nextLine();
            if (input.equals("exit")) {
                break;
            }
            try {
                n = Integer.parseInt(input);
                proc.lanzador(n);
            } catch (NumberFormatException nfe) {
                if (input == "exit") break;
                System.err.println("Fatal");
            }
            
        }
        scanner.close();
        System.out.println("Se acabó la fiesta");

    }

}
