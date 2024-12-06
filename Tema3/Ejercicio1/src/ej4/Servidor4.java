package ej4;
import java.io.*;
import java.net.*;

public class Servidor4 {
    public static void main(String[] args) {
        int port = 2000;

        try (ServerSocket servidorSocket = new ServerSocket(port)) {
            System.out.println("Servidor esperant connexions al port " + port);

            try (Socket socket = servidorSocket.accept()) {
                System.out.println("Client connectat!");

                BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter sortida = new PrintWriter(socket.getOutputStream(), true);

                // Llegeix l'ordre i els nombres enviats pel client
                String ordre = entrada.readLine(); // Operació (suma, resta, etc.)
                double num1 = Double.parseDouble(entrada.readLine());
                double num2 = Double.parseDouble(entrada.readLine());

                double resultat = 0;
                boolean operacioValida = true;

                // Realitza l'operació
                switch (ordre.toLowerCase()) {
                    case "suma":
                        resultat = num1 + num2;
                        break;
                    case "resta":
                        resultat = num1 - num2;
                        break;
                    case "multiplicació":
                        resultat = num1 * num2;
                        break;
                    case "divisió":
                        if (num2 != 0) {
                            resultat = num1 / num2;
                        } else {
                            sortida.println("Error: divisió per zero");
                            operacioValida = false;
                        }
                        break;
                    default:
                        sortida.println("Operació no vàlida");
                        operacioValida = false;
                        break;
                }

                // Retorna el resultat al client si l'operació és vàlida
                if (operacioValida) {
                    sortida.println("El resultat és: " + resultat);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
