package procesos;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Procesos {
    public static final String dirPath = System.getProperty("user.dir") + File.separator + ".." + File.separator + "psp_u1_arv_fill" + File.separator + "src";
    public static Process ejecutaPrograma(String[] command) {

        ProcessBuilder programa = new ProcessBuilder(command);
        programa.directory(new File(dirPath));
        programa.redirectError(new File(dirPath + "errores.txt"));

        try {
            return programa.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void enviar(Process process, String n) {
        OutputStream outS = process.getOutputStream();
        OutputStreamWriter outSW = new OutputStreamWriter(outS);
        BufferedWriter bW = new BufferedWriter(outSW);

        try {
            bW.write(n);
            bW.newLine();
            bW.flush();
            bW.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ArrayList<String> leer(Process proces) {
        ArrayList<String> toReturn = new ArrayList<>();
        // Leemos la salida estándar
        try (InputStream inS = proces.getInputStream();
             InputStreamReader iSR = new InputStreamReader(inS);
             BufferedReader bR = new BufferedReader(iSR)) {

            String linea;
            while ((linea = bR.readLine()) != null) {
                toReturn.add(linea);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al leer la salida del proceso", e);
        }

        // Leemos la salida de error
        try (InputStream errS = proces.getErrorStream();
             InputStreamReader iSR = new InputStreamReader(errS);
             BufferedReader bR = new BufferedReader(iSR)) {

            String linea;
            while ((linea = bR.readLine()) != null) {
                System.err.println(linea); // Imprimimos errores en la consola
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al leer la salida de error del proceso", e);
        }

        return toReturn;
    }
}
