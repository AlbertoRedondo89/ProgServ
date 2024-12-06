package ejercicio2;
import java.io.*;
import java.net.*;
import java.sql.DriverManager;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import org.json.JSONObject;

public class ChatClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 2222;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            String sender = pideNumero("Introduce tu número identificador", scanner);
            String receiver = pideNumero("Introduce el número del destinatario", scanner);

            while (true) {
                System.out.print("Escriu el missatge a enviar: ");
                String text = scanner.nextLine();

                // Construir JSON
                JSONObject message = new JSONObject();
                message.put("sender", sender);
                message.put("receiver", receiver);
                message.put("text", text);
                message.put("time", getTime());

                // Enviar al servidor
                out.println(message.toString());

                // Leer la respuesta del servidor
                StringBuilder conversation = new StringBuilder();
                String response;
                while ((response = in.readLine()) != null && !response.isEmpty()) {
                    conversation.append(response).append("\n");
                }

                System.out.println("Contingut actual de la conversa:");
                System.out.println("---------------------------------");
                System.out.println(conversation);
                System.out.println("---------------------------------");

                // Finalizar si el mensaje es "adeu"
                if (text.equalsIgnoreCase("adeu")) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String pideNumero(String frase, Scanner scanner) {
        String respuesta = "";

        do {
            System.out.print(frase);
            if (scanner.hasNextInt()) {
                respuesta = String.valueOf(scanner.nextInt());
                scanner.nextLine(); // Limpiar el buffer de entrada
                break; // Salir del bucle si la entrada es válida
            } else {
                System.out.println("Por favor, introduce un número válido.");
                scanner.nextLine(); // Descartar la entrada inválida
            }
        } while (true);

        return respuesta;
    }

    private static String getTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return now.format(formatter);
    }
}
