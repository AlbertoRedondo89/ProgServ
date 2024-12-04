package ej2;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Cliente2 {
    public static void main(String[] args) {
        final String HOST = "localhost";
        final int PUERTO = 2222;

        try (Socket socket = new Socket(HOST, PUERTO);
             BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter sortida = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Connectat al servidor.");
            String resposta;

            do {
                System.out.print("Introdueix un número entre 0 i 100: ");
                String numero = scanner.nextLine();
                sortida.println(numero);

                resposta = entrada.readLine();
                System.out.println("Servidor: " + resposta);

            } while (!"Felicitats! Has endevinat el número!".equals(resposta));
        } catch (IOException e) {
            System.err.println("Error al client: " + e.getMessage());
        }
    }
}
