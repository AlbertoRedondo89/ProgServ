import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main {

    public static void main(String[] args) {

        descargaWeb("https://www.google.es", "web.txt");

    }

    private static void descargaWeb(String web, String archivo) {
        try {
            // Crear la URL
            URL url = new URL(web);

            // Abrir la conexión
            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            conexion.setRequestMethod("GET");

            // Leer la respuesta
            BufferedReader lector = new BufferedReader(new InputStreamReader(conexion.getInputStream()));
            BufferedWriter escritor = new BufferedWriter(new FileWriter(archivo));

            String linea;
            while ((linea = lector.readLine()) != null) {
                escritor.write(linea);
                System.out.println(linea);
                escritor.newLine(); // Añadir una nueva línea en el archivo
            }

            // Cerrar los recursos
            lector.close();
            escritor.close();

            System.out.println("HTML descargado y guardado en " + archivo);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}