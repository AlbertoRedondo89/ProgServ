package apps;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class BuscaPalabra {
    public static void main(String[] args) {
        String buscador = args[0];
        String texto = recibeDatos();

        try (PrintWriter out = new PrintWriter(System.out)) {
            out.println("Buscando palabra...");
            if (texto.contains(buscador)) out.println("Palabra encontrada");
            else out.println("La palabra \"" + buscador + "\" no aparece en el texto");
            out.flush();
        }
    }

    private static String recibeDatos() {
        StringBuilder recibido = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            String linea;
            while ((linea = br.readLine())!=null)
                recibido.append(linea).append("\n");
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
        return recibido.toString();
    }
}
