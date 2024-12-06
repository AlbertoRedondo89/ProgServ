package Ejercicio1;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ClienteBD {
    String destino = "localhost";
    int puertoDestino = 2222;

    public ClienteBD() {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(destino, puertoDestino));
            System.out.println("Conectado al servidor en " + destino + ":" + puertoDestino);

            try (BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in));
                 BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {

                while (true) {
                    System.out.println("Introduce instrucción: select, insert, delete, exit");
                    String comando = entrada.readLine();

                    if ("exit".equalsIgnoreCase(comando)) {
                        System.out.println("Cerrando cliente...");
                        break;
                    }

                    writer.println(comando); // Enviar comando al servidor
                    String respuesta = reader.readLine(); // Leer respuesta inicial del servidor
                    System.out.println("Respuesta del servidor: " + respuesta);

                    switch (comando.toLowerCase()) {
                        case "insert":
                            System.out.println("Inserte un ID:");
                            String id = entrada.readLine();
                            writer.println(id);

                            System.out.println("Inserte un Nombre:");
                            String nombre = entrada.readLine();
                            writer.println(nombre);

                            System.out.println("Inserte un Apellido:");
                            String apellido = entrada.readLine();
                            writer.println(apellido);
                            break;

                        case "select":
                        case "delete":
                            System.out.println("Inserte el ID:");
                            id = entrada.readLine();
                            writer.println(id);
                            break;

                        default:
                            System.out.println("Comando no válido");
                            break;
                    }

                    // Leer respuesta final del servidor
                    respuesta = reader.readLine();
                    System.out.println("Respuesta del servidor: " + respuesta);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al conectar al servidor: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new ClienteBD();
    }
}