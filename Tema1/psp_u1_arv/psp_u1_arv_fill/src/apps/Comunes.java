package apps;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

// Clase con un par de métodos comunes a varias clases

public class Comunes {
    public static String recibeDatos() {
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

    public static ArrayList<String> leeArchivo(String url) {
        ArrayList<String> texto = new ArrayList<>();
        try (BufferedReader bw = new BufferedReader(new FileReader(url))) {
            String linea;
            while ((linea = bw.readLine()) != null) {
                texto.add(linea);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return texto;
    }
}
