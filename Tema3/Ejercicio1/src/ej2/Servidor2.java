package ej2;

import java.io.*;
import java.net.*;
import java.util.Random;

public class Servidor2 {
    public static void main(String[] args) {
        int port = 2222;
        Random random = new Random();
        int numeroSecret = random.nextInt(101); // Generar un número entre 0 i 100
        System.out.println("El número secret és: " + numeroSecret);

        try (ServerSocket servidorSocket = new ServerSocket(port)) {
            System.out.println("Servidor esperant connexions al port " + port);

            try (Socket socket = servidorSocket.accept()) {
                System.out.println("Client connectat!");
                BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter sortida = new PrintWriter(socket.getOutputStream(), true);

                String missatgeClient;
                while ((missatgeClient = entrada.readLine()) != null) {
                    try {
                        int numeroClient = Integer.parseInt(missatgeClient);
                        if (numeroClient < numeroSecret) {
                            sortida.println("El número és més gran.");
                        } else if (numeroClient > numeroSecret) {
                            sortida.println("El número és més petit.");
                        } else {
                            sortida.println("Has encertat el número secret!");
                            break;
                        }
                    } catch (NumberFormatException e) {
                        sortida.println("Si us plau, introdueix un número vàlid.");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
