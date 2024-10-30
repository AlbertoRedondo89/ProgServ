package procesos;

import java.io.*;
import java.util.ArrayList;

public class Procesos {
    public static String dirPath = System.getProperty("user.dir") + File.separator + ".." + File.separator + "psp_u1_arv_fill" + File.separator + "src";
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

        return toReturn;
    }
}

/**/
