import java.io.File;

public class Lanzador {

    public void exectSuma(String n1, String n2) {
        String comando = "EjemploProceso/src/Sumador.java";
        try {
            ProcessBuilder pb = new ProcessBuilder("java", comando, n1, n2);
            //pb.redirectOutput(new File("resultado.txt"));
            pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
            pb.redirectError(new File("errores.txt"));
            pb.start();
        } catch (Exception e) {
            System.out.println("Mal");
        }
    }

    public static void main(String[] args) {
        String n1 = args[0];
        String n2 = args[1];
        Lanzador l = new Lanzador();
        l.exectSuma(n1, n2);
        l.exectSuma("10","300");
        l.exectSuma("30", "1200");
        System.out.println("end");

    }

}
