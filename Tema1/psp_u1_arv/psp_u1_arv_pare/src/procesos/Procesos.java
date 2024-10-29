package procesos;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

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

    /*
    *  private void leerTxt() {
        String ruta = System.getProperty("user.dir")+"\\src\\encrypted.txt\"";
        File archivo = new File("src\\encrypted.txt");
        String[] command = new String[5];
        System.arraycopy(rutaGenerica, 0, command, 0, rutaGenerica.length);
        command[3] = "LeeTxt";
        command[4] = ruta;
        if (archivo.exists()) creaProceso(command);
        else {
            System.out.println("Paree que el archvio no existe.");
            System.out.println("escribe 'SI' para crearlo");
            String respuesta = scan.nextLine();
            if (respuesta.equalsIgnoreCase("SI")) cambiarLetra();
        }
    }*/
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

/**/
