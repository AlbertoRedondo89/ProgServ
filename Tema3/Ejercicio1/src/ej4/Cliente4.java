package ej4;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Cliente4 {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 2000;

        try (Socket socket = new Socket(host, port)) {
            System.out.println("Connectat al servidor a " + host + ":" + port);

            PrintWriter sortida = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Scanner scanner = new Scanner(System.in);

            // Demana l'operació i els nombres a l'usuari
            System.out.print("Introdueix l'operació (suma, resta, multiplicació, divisió): ");
            String ordre = scanner.nextLine();

            System.out.print("Introdueix el primer nombre: ");
            double num1 = scanner.nextDouble();

            System.out.print("Introdueix el segon nombre: ");
            double num2 = scanner.nextDouble();

            // Envia la informació al servidor
            sortida.println(ordre);
            sortida.println(num1);
            sortida.println(num2);

            // Llegeix i mostra la resposta del servidor
            String resposta = entrada.readLine();
            System.out.println("Resposta del servidor: " + resposta);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
