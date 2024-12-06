package Practica2;
import java.io.*;
import java.net.*;
import java.util.Scanner;
import org.json.JSONObject;

public class ClienteChat {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 2222);
             BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            System.out.print("Indica el teu número: ");
            String sender = scanner.nextLine();

            System.out.print("Indica el número del receptor: ");
            String receiver = scanner.nextLine();

            System.out.println("Escriu el missatge a enviar ('adeu' per finalitzar):");

            while (true) {
                String text = scanner.nextLine();

                // Construir el JSON con los parámetros
                JSONObject message = new JSONObject();
                message.put("sender", sender);
                message.put("receiver", receiver);
                message.put("text", text);

                // Enviar el mensaje al servidor
                output.println(message.toString());

                if (text.equalsIgnoreCase("adeu")) {
                    System.out.println("Chat finalitzat.");
                    break;
                }

                // Leer y mostrar la conversación completa
                String conversation = input.readLine();
                System.out.println("Contingut actual de la conversa:");
                System.out.println("---------------------------------");
                System.out.println(conversation);
                System.out.println("---------------------------------");
            }
        } catch (IOException e) {
            System.err.println("Error en el cliente: " + e.getMessage());
        }
    }
}
