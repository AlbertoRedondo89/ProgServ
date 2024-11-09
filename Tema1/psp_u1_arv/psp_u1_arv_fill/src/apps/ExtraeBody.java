package apps;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
* Recibe la web con recibeDatos() y la guarda en un String
* Recorre el texto con extraeBody() y genera un nuevo String que incluye solo el body (si no encuentra body, devolverá un String vacío)
* guarda el String de body en un nuevo archivo html
* */

public class ExtraeBody {
    public static void main(String[] args) {
        String webCompleta = String.valueOf(Comunes.leeArchivo(args[0]));
        String inicioHtml = "<html>\n" +
                "  <head>\n" +
                "    <meta charset=\"utf-8\" />\n" +
                "    <title>Pagina Encriptada</title>\n" +
                "\n </head>";
        String finalHtml = "</html>";
        String webSoloBody = inicioHtml + extraeBody(webCompleta) + finalHtml;

        String fileName = "index.html";

        boolean guardado = guardarEnArchivo(webSoloBody,fileName);

        try (PrintWriter out = new PrintWriter(System.out)) {
            out.println("Creando archivo...");
            if (guardado) out.println("Archivo creado correctamente");
            else out.println("No te lo vas a creer, pero... Algo ha ido mal.");
            out.flush();
        }
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
