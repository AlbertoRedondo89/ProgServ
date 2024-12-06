package ej3;
import java.io.*;
import java.net.*;

public class Servidor3 {
    public static void main(String[] args) {
        int port = 2222;
        String fitxer = "direcciones.txt";

        try (ServerSocket servidorSocket = new ServerSocket(port)) {
            System.out.println("Servidor esperant connexions al port " + port);

            try (Socket socket = servidorSocket.accept()) {
                System.out.println("Client connectat!");

                BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter sortida = new PrintWriter(socket.getOutputStream(), true);

                String jsonRebut = entrada.readLine(); // Llegeix el JSON del client
                if (jsonRebut != null) {
                    System.out.println("JSON rebut: " + jsonRebut);

                    // Escriu el JSON al fitxer
                    try (FileWriter fileWriter = new FileWriter(fitxer, true)) {
                        fileWriter.write(jsonRebut + "\n");
                        System.out.println("Dades emmagatzemades al fitxer " + fitxer);
                    }

                    // Envia resposta al client
                    sortida.println("OK");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

