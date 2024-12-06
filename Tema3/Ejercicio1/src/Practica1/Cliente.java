package Practica1;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 2222);
             BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Conectado al servidor.");
            System.out.print("Comando (insert, select, delete): ");
            String command = scanner.nextLine();
            output.println(command);

            switch (command.toLowerCase()) {
                case "insert":
                    System.out.print("ID: ");
                    output.println(scanner.nextLine());
                    System.out.print("Nombre: ");
                    output.println(scanner.nextLine());
                    System.out.print("Apellido: ");
                    output.println(scanner.nextLine());
                    break;
                case "select":
                    System.out.print("ID: ");
                    output.println(scanner.nextLine());
                    break;
                case "delete":
                    System.out.print("ID: ");
                    output.println(scanner.nextLine());
                    break;
                default:
                    System.out.println("Comando desconocido.");
                    return;
            }

            System.out.println("Respuesta del servidor: " + input.readLine());
        } catch (IOException e) {
            System.err.println("Error en la conexión: " + e.getMessage());
        }
    }
}

