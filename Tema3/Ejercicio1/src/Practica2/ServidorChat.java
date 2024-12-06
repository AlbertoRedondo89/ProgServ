package Practica2;
import java.io.*;
import java.net.*;
import java.util.*;
import org.json.JSONObject;

public class ServidorChat {
    private static final String CONVERSATIONS_FOLDER = "conversaciones/";

    public static void main(String[] args) {
        // Crear la carpeta de conversaciones si no existe
        File folder = new File(CONVERSATIONS_FOLDER);
        if (!folder.exists()) {
            folder.mkdir();
        }

        try (ServerSocket serverSocket = new ServerSocket(2222)) {
            System.out.println("Servidor de chat en espera de clientes...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado.");
                new Thread(new ClientHandler(clientSocket)).start();
            }
        } catch (IOException e) {
            System.err.println("Error al iniciar el servidor: " + e.getMessage());
        }
    }

    static class ClientHandler implements Runnable {
        private final Socket clientSocket;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            try (
                    BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true)
            ) {
                while (true) {
                    String jsonString = input.readLine();
                    if (jsonString == null) break;

                    // Parsear el JSON recibido
                    JSONObject message = new JSONObject(jsonString);
                    String sender = message.getString("sender");
                    String receiver = message.getString("receiver");
                    String text = message.getString("text");

                    if (text.equalsIgnoreCase("adeu")) {
                        output.println("Chat finalitzat.");
                        break;
                    }

                    // Nombre del archivo de la conversación
                    String conversationFile = CONVERSATIONS_FOLDER + sender + "_" + receiver + ".txt";

                    // Escribir el mensaje en el archivo
                    appendToFile(conversationFile, sender + ": " + text);

                    // Leer el contenido completo de la conversación
                    String conversationContent = readFile(conversationFile);

                    // Enviar la conversación completa al cliente
                    output.println(conversationContent);
                }
            } catch (IOException e) {
                System.err.println("Error al manejar cliente: " + e.getMessage());
            }
        }

        private void appendToFile(String fileName, String content) throws IOException {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
                writer.write(content);
                writer.newLine();
            }
        }

        private String readFile(String fileName) throws IOException {
            File file = new File(fileName);
            if (!file.exists()) return "";

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
                return content.toString();
            }
        }
    }
}
