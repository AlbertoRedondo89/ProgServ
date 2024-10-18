import java.io.*;
import java.util.Scanner;

public class Main {

    static final String dirPath = "C:\\Users\\alber\\Desktop\\ConexionConGit\\Prog\\ProgServ\\EjercicioMultiproceso2_ModificaString\\src";
    static final String [] command = {
            "java",
            "ModificaString.java"
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

        Scanner scanner = new Scanner(System.in);

        System.out.println("Dime algo, pichón");
        String input = "";
        while (true)  {
            input = scanner.nextLine();
            try {
                if (input.equals("exit")) break;
                Process process = executarPrograma();
                enviar(process, input);
                String retorn = llegir(process);
                System.out.println("Pare: " + retorn);
            } catch (NumberFormatException | IOException nfe) {
                System.err.println("Fatal");
            }

        }
        scanner.close();
        System.out.println("Se acabó la fiesta");

    }
}