import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClienteBBDD {
    private static final String HOST = "localhost";
    private static final int PUERTO = 2222;

    public static void main(String[] args) {
        try (Socket socket = new Socket(HOST, PUERTO);
             BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter salida = new PrintWriter(socket.getOutputStream(), true);
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Conectado al servidor.");

            while (true) {
                System.out.print("Introduce un comando (insert, select, delete o exit): ");
                String comando = scanner.nextLine();

                if (comando.equalsIgnoreCase("exit")) {
                    System.out.println("Cerrando conexión.");
                    break;
                }

                salida.println(comando);

                switch (comando.toLowerCase()) {
                    case "insert":
                        System.out.print("ID: ");
                        String id = scanner.nextLine();
                        System.out.print("Nombre: ");
                        String nombre = scanner.nextLine();
                        System.out.print("Apellido: ");
                        String apellido = scanner.nextLine();

                        salida.println(id);
                        salida.println(nombre);
                        salida.println(apellido);
                        break;
                    case "select":
                        System.out.print("ID: ");
                        salida.println(scanner.nextLine());
                        break;
                    case "delete":
                        System.out.print("ID: ");
                        salida.println(scanner.nextLine());
                        break;
                    default:
                        System.out.println("Comando no reconocido.");
                        continue;
                }

                String respuesta = entrada.readLine();
                System.out.println("Respuesta del servidor: " + respuesta);
            }
        } catch (IOException e) {
            System.err.println("Error en el cliente: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
