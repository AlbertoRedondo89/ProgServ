package apps;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class AbreNavegador {

    // Recibe la ruta a index.html desde args[]
    // Comprueba que el archivo existe (aunque el padre también lo comprueba)
    // Detecta el navegador predeterminado del sistema y abre el html

    public static void main(String[] args) {
        String ruta = args[0];
        File archivoHtml = new File(ruta);
        if (archivoHtml.exists()) {
            try {
                Desktop.getDesktop().browse(archivoHtml.toURI());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.err.println("El archivo " + archivoHtml.getAbsolutePath() + " no existe");
        }
    }
}
