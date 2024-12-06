package ej2;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Cliente2 {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 2222;

        try (Socket socket = new Socket(host, port)) {
            System.out.println("Connectat al servidor a " + host + ":" + port);

            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter sortida = new PrintWriter(socket.getOutputStream(), true);
            Scanner scanner = new Scanner(System.in);

            String respostaServidor;
            while (true) {
                System.out.print("Introdueix un número: ");
                String numero = scanner.nextLine();
                sortida.println(numero);
                respostaServidor = entrada.readLine();
                System.out.println("Servidor: " + respostaServidor);

                if (respostaServidor.equals("Has encertat el número secret!")) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
