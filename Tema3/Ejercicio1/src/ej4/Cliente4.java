package ej4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Cliente4 {
    private static final String HOST = "localhost";
    private static final int PUERTO = 2000;

    public static void main(String[] args) {
        try (Socket socket = new Socket(HOST, PUERTO);
             BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Conectat al servidor.");
            String operacio;

            while (true) {
                // Demana l'operació
                System.out.print("Introdueix l'operació (suma, resta, multiplicació, divisió) o 'exit' per sortir: ");
                operacio = scanner.nextLine();

                // Comprova si l'usuari vol sortir
                if (operacio.equalsIgnoreCase("exit")) {
                    System.out.println("Sortint del client...");
                    break;
                }

                // Demana els dos nombres
                System.out.print("Introdueix el primer nombre: ");
                double num1 = scanner.nextDouble();
                System.out.print("Introdueix el segon nombre: ");
                double num2 = scanner.nextDouble();
                scanner.nextLine(); // Consumir el salt de línia després del segon nombre

                // Envia les dades al servidor
                salida.println(operacio);
                salida.println(num1);
                salida.println(num2);

                // Rep el resultat
                String resposta = entrada.readLine();
                System.out.println("El resultat de l'operació és: " + resposta);
            }
        } catch (IOException e) {
            System.err.println("Error en el client: " + e.getMessage());
        }
    }
}