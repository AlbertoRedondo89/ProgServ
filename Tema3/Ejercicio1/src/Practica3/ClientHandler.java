package Practica3;

import org.json.JSONObject;

import java.io.*;
import java.net.Socket;

class ClientHandler extends Thread {
    private Socket socket;
    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true)
        ) {
            String input;
            while ((input = in.readLine()) != null) {
                // Parsear el JSON recibido
                JSONObject message = new JSONObject(input);
                String sender = message.getString("sender");
                String receiver = message.getString("receiver");
                String text = message.getString("text");
                String hora = message.getString("time");

                System.out.println(message);

                // Finalizar si el mensaje es "adeu"
                if (text.equalsIgnoreCase("adeu")) {
                    out.println("Chat finalitzat.");
                    break;
                }

                // Actualizar conversación
                String fileName = "chat_" + Math.min(Long.parseLong(sender), Long.parseLong(receiver)) +
                        "_" + Math.max(Long.parseLong(sender), Long.parseLong(receiver)) + ".txt";
                File file = new File(fileName);

                try (FileWriter writer = new FileWriter(file, true)) {
                    writer.write(sender + ": " + text + "\t\t" + hora + "\n");
                }

                // Enviar la conversación actualizada al cliente
                StringBuilder conversation = new StringBuilder();
                try (BufferedReader fileReader = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = fileReader.readLine()) != null) {
                        conversation.append(line).append("\n");
                    }
                }

                out.println(conversation);
            }
        } catch (IOException e) {
            System.out.println("Conexión perdida con un cliente");
            System.out.println(e);
        }
    }
}