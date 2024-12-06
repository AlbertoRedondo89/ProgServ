package ej2;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class Servidor2 {
    public static void main(String[] args) {
        final int PUERTO = 2222;
        Random random = new Random();
        int numeroSecreto = random.nextInt(101); // Número entre 0 y 100

        System.out.println("El número secreto es: " + numeroSecreto);

        try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {
            System.out.println("Servidor esperant connexions al port " + PUERTO + "...");

            try (Socket socket = serverSocket.accept();
                 BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter sortida = new PrintWriter(socket.getOutputStream(), true)) {

                System.out.println("Client connectat!");

                String missatge;
                while ((missatge = entrada.readLine()) != null) {
                    int numeroClient;
                    try {
                        numeroClient = Integer.parseInt(missatge);
                    } catch (NumberFormatException e) {
                        sortida.println("ERROR: Introdueix un número vàlid.");
                        continue;
                    }

                    if (numeroClient < numeroSecreto) {
                        sortida.println("El número és major.");
                    } else if (numeroClient > numeroSecreto) {
                        sortida.println("El número és menor.");
                    } else {
                        sortida.println("Felicitats! Has endevinat el número!");
                        break;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error al servidor: " + e.getMessage());
        }
    }
}