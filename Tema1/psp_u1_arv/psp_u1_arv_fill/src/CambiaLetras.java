import java.io.*;

public class CambiaLetras {

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

        String fileName = "Cambialetras_" + letraACambiar + "_" + nuevaLetra + ".txt";       // Crear el nombre del archivo
        String currentDirPath = System.getProperty("user.dir");                              // Obtener el directorio actual
        String parentDirPath = currentDirPath.substring(0, currentDirPath.length() - 19);    // Eliminar las últimas 19 letras de la ruta
        // Construir la ruta completa al archivo
        String filePath = parentDirPath + "psp_u1_arv_pare" + File.separator + "src" + File.separator + fileName;

        try (PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(filePath)))) {
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
