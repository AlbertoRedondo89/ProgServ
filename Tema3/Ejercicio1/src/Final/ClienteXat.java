package Final;
import java.io.*;
import java.net.*;
import java.util.Scanner;
import org.json.JSONObject;

public class ClienteXat {
    private static final String HOST = "localhost";
    private static final int PUERTO = 3000;

    public static void main(String[] args) {
        try (Socket socket = new Socket(HOST, PUERTO);
             BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            System.out.print("Indica el teu Num: ");
            String numOrigen = scanner.nextLine();

            System.out.print("Indica el Num del receptor: ");
            String numDestino = scanner.nextLine();

            while (true) {
                // Llegir el text a enviar
                System.out.print("Escriu el missatge a enviar: ");
                String texto = scanner.nextLine();

                // Construir el JSON manualmente
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("numOrigen", numOrigen);
                jsonObject.put("numDestino", numDestino);
                jsonObject.put("texto", texto);

                // Enviar al servidor
                salida.println(jsonObject.toString());

                // Llegir la conversa actualitzada
                String resposta = entrada.readLine();
                System.out.println("Contingut actual de la conversa:");
                System.out.println("---------------------------------");
                System.out.println(resposta);
                System.out.println("---------------------------------");

                // Terminar si el missatge és "adeu"
                if (texto.equalsIgnoreCase("adeu")) {
                    System.out.println("Finalitzant xat...");
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("Error en el client: " + e.getMessage());
        }
    }
}
