package apps;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class BuscaPalabra {

    /*
    * Recibe por args[] el String que buscará en la web
    * Recibe la web en recibeDatos()
    * busca el String en la web recibida y devuelve si el texto contiene ese String o no
    * */

    public static void main(String[] args) {
        String buscador = args[0];
        String texto = Comunes.recibeDatos();

        try (PrintWriter out = new PrintWriter(System.out)) {
            out.println("Buscando palabra...");
            if (texto.contains(buscador)) out.println("Palabra encontrada");
            else out.println("La palabra \"" + buscador + "\" no aparece en el texto");
            out.flush();
        }
    }

}
