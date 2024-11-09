package apps;

import java.io.*;
import java.util.ArrayList;

public class LeeTxt {

    /*
    * Recibe por argumentos la ruta del archivo a leer
    * leerArchivo, para sorpresa de nadie, lee el archivo
    * escribeArchivo lo saca por un System.out para que lo reciba el padre
    * */

    public static void main(String[] args) {
        String ruta = args[0];
        escribeArchivo(Comunes.leeArchivo(ruta));
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
