package apps;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtraeBody {
    public static void main(String[] args) {
        String webCompleta = recibeDatos();
        String webSoloBody = extraeBody(webCompleta);

        String fileName = "index.html";                                                   // Crear el nombre del archivo
        String currentDirPath = System.getProperty("user.dir");                              // Obtener el directorio actual
        String parentDirPath = currentDirPath.substring(0, currentDirPath.length() - 19);    // Eliminar las últimas 19 letras de la ruta
        // Construir la ruta completa al archivo
        String filePath = parentDirPath + "psp_u1_arv_pare" + File.separator + "src" + File.separator + fileName;

        boolean guardado = guardarEnArchivo(webSoloBody,filePath);

        try (PrintWriter out = new PrintWriter(System.out)) {
            out.println("Creando archivo...");
            if (guardado) out.println("Archivo creado correctamente");
            else out.println("No te lo vas a creer, pero... Algo ha ido mal.");
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
    private static String extraeBody(String html) {
        String regex = "(<body[^>]*>)(.*?)(</body>)";
        Pattern pattern = Pattern.compile(regex, Pattern.DOTALL | Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(html);

        if (matcher.find()) {
            // Esto guarda tres grupos: La primera etiqueta body, el interior y la de cierre
            return matcher.group(1) + matcher.group(2) + matcher.group(3);
        }

        return ""; // Si no encontrase body, devolvería un String vacío
    }
    private static boolean guardarEnArchivo(String contenido, String nombreArchivo) {
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(nombreArchivo)))) {
            out.print(contenido);
            return true;
        } catch (IOException e) {
            System.err.println("Error al guardar el archivo: " + e.getMessage());
            return false;
        }
    }
}
