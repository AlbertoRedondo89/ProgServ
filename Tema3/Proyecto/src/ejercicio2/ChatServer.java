package ejercicio2;

import java.io.*;
import java.net.*;

public class ChatServer {
    private static final int PORT = 2222;

    public static void main(String[] args) {
        System.out.println("Servidor iniciat...");

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                new ClientHandler(clientSocket).start();
            }
        } catch (IOException e) {
            System.out.println("Conexión perdida con un cliente");
            System.out.println(e);
        }
    }
}


