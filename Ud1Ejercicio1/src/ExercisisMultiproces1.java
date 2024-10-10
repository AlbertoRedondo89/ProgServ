import java.io.File;
import java.util.Scanner;

public class ExercisisMultiproces1 {

    public static void main(String[] args) {
        ExercisisMultiproces1 prueba = new ExercisisMultiproces1();
    }

    public void creaProceso() {
        String comando = "Ud1Ejercicio1/src/Senar.java";
        try {
            ProcessBuilder pb = new ProcessBuilder(comando, "5");
            //pb.redirectOutput(new File("resultado.txt"));
            pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
            pb.redirectError(new File("errores.txt"));
            pb.start();
        } catch (Exception e) {
            System.out.println("Mal");
        }
    }

}
