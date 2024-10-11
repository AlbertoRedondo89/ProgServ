import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class ModificaString {
    public static void main(String[] args) throws IOException {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String texto = null;

        try {
            texto = in.readLine();
            System.out.println("Hijo dice: " + hablaHijo(texto));
        } catch (IOException e) {
            System.out.println("Hijo, papá, no ha ido bien... ");
        }

        System.out.println(hablaHijo(texto));
        in.close();

    }

    public static String hablaHijo(String txt) {
        String texto = txt.toUpperCase();
        StringBuilder sb = new StringBuilder();
        sb.append("El hijo dice: ");
        String vocales = "AEIOU";

        for (int i = 0; i < texto.length(); i++) {
            char c = texto.charAt(i);
            if (vocales.indexOf(c) != -1) sb.append('_');
            else sb.append(c);
        }

        return sb.toString();
    }
}