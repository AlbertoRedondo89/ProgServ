import java.io.*;

public class CuentaCaracteres {
    public static void main(String[] args) {
        char buscador = args[0].charAt(0);
        StringBuilder recibido = new StringBuilder();
        int resultado = 0;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            String linea;
            while ((linea = br.readLine())!=null)
                recibido.append(linea).append("\n");
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }

        resultado = cuenta(recibido.toString(), buscador);
        String respuesta = "El total de veces que aparece \"" + buscador + "\" es: " + resultado;

        try (PrintWriter out = new PrintWriter(System.out)) {
            out.println(respuesta);
        }
    }

    private static int cuenta(String texto, char buscador) {
        int total = 0;

        for (char c : texto.toCharArray()){
            if (c == buscador) total++;
        }
        return total;
    }
}
