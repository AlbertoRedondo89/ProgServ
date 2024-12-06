package ej4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class Servidor4 {
    private static final int PUERTO = 2000;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {
            System.out.println("Servidor en funcionament al port " + PUERTO);

            while (true) {
                try (Socket socket = serverSocket.accept();
                     BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     PrintWriter salida = new PrintWriter(socket.getOutputStream(), true)) {

                    System.out.println("Client connectat.");

                    // Llegeix la ordre i els nombres
                    String operacio = entrada.readLine();
                    double num1 = Double.parseDouble(entrada.readLine());
                    double num2 = Double.parseDouble(entrada.readLine());

                    // Realitza el càlcul
                    double resultat = calcular(operacio, num1, num2);

                    // Envia el resultat al client
                    salida.println(resultat);
                    System.out.println("Operació processada: " + operacio + " " + num1 + " " + num2 + " = " + resultat);
                } catch (Exception e) {
                    System.err.println("Error processant l'operació: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Error al servidor: " + e.getMessage());
        }
    }

    private static double calcular(String operacio, double num1, double num2) throws IllegalArgumentException {
        switch (operacio.toLowerCase()) {
            case "suma":
                return num1 + num2;
            case "resta":
                return num1 - num2;
            case "multiplicació":
                return num1 * num2;
            case "divisió":
                if (num2 == 0) {
                    throw new IllegalArgumentException("Divisió per zero no permesa.");
                }
                return num1 / num2;
            default:
                throw new IllegalArgumentException("Operació no reconeguda: " + operacio);
        }
    }
}