package Ejercicio1;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorBD {
    static boolean exit = false;
    int puertoDestino = 2222;

    public ServidorBD() {
        try (ServerSocket serverSocket = new ServerSocket(puertoDestino)) {
            System.out.println("Servidor iniciado en el puerto " + puertoDestino);

            while (!exit) {
                System.out.println("Esperando conexiones...");
                try (Socket server = serverSocket.accept();
                     BufferedReader reader = new BufferedReader(new InputStreamReader(server.getInputStream()));
                     PrintWriter pw = new PrintWriter(server.getOutputStream(), true)) {

                    System.out.println("Cliente conectado");

                    String id;

                    while (true) {
                        String comando = reader.readLine();
                        if (comando == null) {
                            System.out.println("Cliente desconectado");
                            break;
                        }

                        switch (comando.toLowerCase()) {
                            case "insert":
                                pw.println("Inserte los datos, por favor (ID, Nombre, Apellido):");

                                id = reader.readLine();
                                String nombre = reader.readLine();
                                String apellido = reader.readLine();

                                if (Tools.insertarEnArchivo(id, nombre, apellido)) {
                                    pw.println("Datos insertados correctamente: " + id + " " + nombre + " " + apellido);
                                } else {
                                    pw.println("Error: el ID ya existe.");
                                }
                                break;

                            case "select":
                                pw.println("Inserte el ID del usuario a buscar:");
                                id = reader.readLine();

                                String resultado = Tools.buscarEnArchivo(id);
                                if (resultado != null) {
                                    pw.println("Datos encontrados: " + resultado);
                                } else {
                                    pw.println("Error: Elemento no encontrado.");
                                }
                                break;

                            case "delete":
                                pw.println("Inserte el ID del usuario a eliminar:");
                                id = reader.readLine();

                                if (Tools.eliminarDeArchivo(id)) {
                                    pw.println("Usuario con ID=" + id + " eliminado.");
                                } else {
                                    pw.println("Error: Elemento no encontrado.");
                                }
                                break;

                            default:
                                pw.println("Comando no reconocido. Por favor, introduzca select, insert o delete.");
                                pw.println("Inténtelo de nuevo");
                                pw.flush();
                                break;
                            }
                    }
                } catch (IOException e) {
                    System.out.println("Error en la comunicación con el cliente: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Error al iniciar el servidor: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        ServidorBD servidor = new ServidorBD();
    }
}