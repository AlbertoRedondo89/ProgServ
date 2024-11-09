package apps;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class DescargaWeb {

    /*
     *Este programa recibe desde args[0] la url de la web, accede a la web, copia su contenido y lo envía fuera con un DataOutputStream para que lo reciba el padre.
     */
    public static void main(String[] args) {
        // Espera que la URL desde args[]
        if (args.length == 0) {
            System.out.println("No se proporcionó la URL.");
            return; // Termina el proceso si no hay URL
        }

        String web = args[0]; // La URL la da el primer argumento
        String contenido = descargaWeb(web);
        System.out.println("Contenido descargado:");
        DataOutputStream ops = new DataOutputStream(new BufferedOutputStream(System.out));
        try {
            ops.write(contenido.getBytes());
            ops.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String descargaWeb(String web) {
        StringBuilder sb = new StringBuilder();
        System.out.println("Iniciando descarga desde: " + web);

        try {
            // Crear la URL de la web
            URI uri = new URI(web);
            URL url = uri.toURL();

            // Abre la conexión para acceder a la web
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            conexion.setRequestMethod("GET");
            conexion.setConnectTimeout(5000); // Timeout de 5 segundos
            conexion.setReadTimeout(5000); // Timeout de lectura

            // Obtener el código de respuesta para posibles errores
            int responseCode = conexion.getResponseCode();
            System.out.println("Código de respuesta: " + responseCode);

            if (responseCode == HttpURLConnection.HTTP_OK) { // Comprobación de que la conexión es correcta, por si la url no funciona bien o da problemas
                BufferedReader lector = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
                String linea;
                while ((linea = lector.readLine()) != null) {
                    sb.append(linea).append("\n");
                }
                lector.close();
            } else {
                System.out.println("Error: No se pudo descargar. Código de respuesta: " + responseCode);
            }

        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
