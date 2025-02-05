package ejercicio2;
import java.io.*;
import java.net.*;
import java.sql.DriverManager;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import org.json.JSONObject;
/*
* El cliente se conecta al servidor
* Pide al usuario un id para el y otro para el receptor
* en bucle va pidiendo mensajes al usuario que envía junto a los ids en formato JSON al servidor
* el servidor guarda los datos en un archivo y envía al usuario la conversación completa
* */

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

                // Constructor del JSON que se envía al servidor
                JSONObject message = new JSONObject();
                message.put("sender", sender);
                message.put("receiver", receiver);
                message.put("text", text);
                message.put("time", getTime());

                // Enviar al servidor el JSON
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

                // Acaba el programa si el mensaje es "adeu"
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
                break;
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
