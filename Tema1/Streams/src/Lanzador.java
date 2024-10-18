import java.io.*;
import java.util.Scanner;

public class Lanzador{

    static final String dirPath = "C:\\Users\\alber\\Desktop\\ConexionConGit\\Prog\\ProgServ\\Streams\\src";
    static final String [] command = {
            "java",
            "Sqrt.java"
    };

    private static Process executarPrograma() throws IOException {
        ProcessBuilder programa = new ProcessBuilder(command);
        programa.directory(new File(dirPath));
        programa.redirectError(new File(dirPath + "errores.txt"));
        return programa.start();
    }

    private static void enviar(Process proces, String n) throws IOException {
        OutputStream outS = proces.getOutputStream();
        OutputStreamWriter outSW = new OutputStreamWriter(outS);    // Esto convierte la info en bytes para poderla enviar
        BufferedWriter outBW = new BufferedWriter(outSW);           // Esto pues agiliza un poco las cosas

        outBW.write(n);
        outBW.newLine();
        outBW.flush();              // guarda la info
        outBW.close();              // si no pones close, esto se queda ahi abierto dando por saco
    }

    private static String llegir(Process proces) throws IOException {
        InputStream inS = proces.getInputStream();
        InputStreamReader iSR = new InputStreamReader(inS);
        BufferedReader iBR = new BufferedReader(iSR);

        return iBR.readLine();
        // Aqui hace menos falta hacer el close
    }

    public static void main(String[] args) {

        try (Scanner scan = new Scanner(System.in)) {

            while (true) {
                System.out.println("Pare: Dame un número, pichón!");
                String n = scan.nextLine();

                if (n.equals("exit")) return;

                Process process = executarPrograma();
                enviar(process, n);
                String retorn = llegir(process);
                System.out.println("Pare: " + retorn);

                System.out.println("Pare: Fi");

            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

    }

}



