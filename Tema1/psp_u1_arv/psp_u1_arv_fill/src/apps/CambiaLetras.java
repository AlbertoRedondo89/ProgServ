package apps;

import java.io.*;

public class CambiaLetras {

    /*
    * Recibe dos argumentos, selecciona sólo el primer caracter de cada uno
    * Recibe el texto de la web y lo guarda en recibido
    * Envía recibido y los dos char al metodo cambiador -> recorre recibido y cambia los valores de los char que encuentra. A la vez, genera el archvio txt
    * El metodo cambiador devuelve un boolean para comprobar que se ha creado bien el archivo.
    * Finalmente se devuelve una sentencia de confirmacion o de error al usuario
    * */

    public static void main(String[] args) {
        boolean cambiado;
        char letraACambiar = args[0].charAt(0);
        char nuevaLetra = args[1].charAt(0);
        StringBuilder recibido = new StringBuilder();

        // Leer el texto de entrada
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                recibido.append(linea).append("\n");
            }
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }

        // Ejecutar el cambio y escribir el archivo en la carpeta del programa padre
        cambiado = cambiador(recibido.toString(), letraACambiar, nuevaLetra);

        // Imprimir resultado
        try (PrintWriter out = new PrintWriter(System.out)) {
            if (cambiado) {
                out.println("Archivo creado con éxito");
            } else {
                out.println("Algo ha salido terriblemente mal");
            }
        }
    }

    private static boolean cambiador(String texto, char letraACambiar, char nuevaLetra) {
        boolean fin;

        String fileName = "encrypted.txt";
        try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(fileName)))) {
            // Cambiar letras y escribir en el archivo
            for (char c : texto.toCharArray()) {
                if (c == letraACambiar) c = nuevaLetra;
                pw.print(c);
            }
            fin = true;
        } catch (IOException e) {
            System.err.println("Error al escribir el archivo: " + e.getMessage());
            fin = false;
        }
        return fin;
    }
}
