import java.io.*;
import java.util.Scanner;

public class Main {
    static final String dirPath = "C:\\Users\\alber\\Desktop\\GIT\\Programación\\ProgServ\\Ejercicio3.2\\src";
    //static final String dirPath = "C:\\Users\\alber\\Desktop\\ConexionConGit\\Prog\\ProgServ\\Ejercicio3.2\\src";
    static final String [] command = {
            "java",
            "Main.java"
    };

    private static Process executarPrograma() throws IOException {
        ProcessBuilder programa = new ProcessBuilder(command);
        programa.directory(new File(dirPath));
        programa.redirectError(new File(dirPath + "\\errores.txt"));
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

        StringBuilder salida = new StringBuilder();
        String linea = "";
        while ((linea = iBR.readLine()) != null) {
            salida.append(linea).append("\n");
        }
        return salida.toString().trim();
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
                System.out.println("Pare envía el mensaje: " + input);
                    //Envia mensaje al hijo
                enviar(process, input);
                    //El wait para esperar a la respuesta antes de seguir
                process.waitFor();
                    //Lee el retorno del hijo
                String retorn = llegir(process);
                System.out.println(retorn);
            } catch (NumberFormatException | IOException nfe) {
                System.err.println("Fatal");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
        scanner.close();
        System.out.println("Se acabó la fiesta");

    }
}