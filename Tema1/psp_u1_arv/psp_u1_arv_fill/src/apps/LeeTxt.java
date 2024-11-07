package apps;

import java.io.*;
import java.util.ArrayList;

public class LeeTxt {

    public static void main(String[] args) {
        String ruta = args[0];
        escribeArchivo(leeArchivo(ruta));
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
    public static void escribeArchivo(ArrayList<String> texto) {
        try (PrintWriter out = new PrintWriter(System.out)) {
            for (String linea : texto) {
                out.println(linea);
            }
            out.flush();
        }
    }
}
