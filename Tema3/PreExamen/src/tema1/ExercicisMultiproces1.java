package tema1;// ExercicisMultiproces1.java
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class ExercicisMultiproces1 {
    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String input;

        while (true) {
            try {
                System.out.print("Introdueix un nombre: ");
                input = reader.readLine();

                if ("exit".equalsIgnoreCase(input)) {
                    System.out.println("Sortint del programa.");
                    break;
                }

                int numero = Integer.parseInt(input);

                // Crear el procés fill
                ProcessBuilder processBuilder = new ProcessBuilder(
                        "java", "ExercicisMultiproces1_ParellSenar", String.valueOf(numero));

                processBuilder.redirectErrorStream(true); // Combina stdout i stderr
                Process process = processBuilder.start();

                // Llegeix la sortida del procés fill
                try (BufferedReader processOutput = new BufferedReader(
                        new InputStreamReader(process.getInputStream()))) {
                    String line;
                    while ((line = processOutput.readLine()) != null) {
                        System.out.println(line);
                    }
                }

                process.waitFor(); // Espera que el procés fill acabi

            } catch (NumberFormatException e) {
                System.out.println("Error: Has d'introduir un nombre enter positiu o 'exit'.");
            } catch (Exception e) {
                System.out.println("S'ha produït un error: " + e.getMessage());
            }
        }
    }
}
