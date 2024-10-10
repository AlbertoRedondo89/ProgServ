import java.util.Arrays;

public class ModificaString {
    public static void main(String[] args) {

        ModificaString mod = new ModificaString();
        String texto = Arrays.toString(args);

        System.out.println(hablaHijo(texto));

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