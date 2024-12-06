import java.io.*;
import java.net.*;
import java.util.*;

public class ServidorBBDD {
    private static final String BBDD_FILE = "bbdd.txt";
    private static final int PUERTO = 2222;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {
            System.out.println("Servidor en funcionamiento en el puerto " + PUERTO);

            while (true) {
                try (Socket socket = serverSocket.accept();
                     BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     PrintWriter salida = new PrintWriter(socket.getOutputStream(), true)) {

                    String comando = entrada.readLine();
                    System.out.println("Comando recibido: " + comando);

                    switch (comando.toLowerCase()) {
                        case "insert":
                            handleInsert(entrada, salida);
                            break;
                        case "select":
                            handleSelect(entrada, salida);
                            break;
                        case "delete":
                            handleDelete(entrada, salida);
                            break;
                        default:
                            salida.println("Comando no reconocido.");
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error en el servidor: " + e.getMessage());
        }
    }

    private static void handleInsert(BufferedReader entrada, PrintWriter salida) throws IOException {
        String id = entrada.readLine();
        String nombre = entrada.readLine();
        String apellido = entrada.readLine();
        System.out.println("hola que tal");
        List<String> registros = leerBBDD();
        for (String registro : registros) {
            if (registro.startsWith(id + ",")) {
                salida.println("Error: El ID ya existe.");
                return;
            }
        }

        escribirBBDD(id + "," + nombre + "," + apellido);
        salida.println("Datos insertados en la BBDD.");
    }

    private static void handleSelect(BufferedReader entrada, PrintWriter salida) throws IOException {
        String id = entrada.readLine();

        List<String> registros = leerBBDD();
        for (String registro : registros) {
            if (registro.startsWith(id + ",")) {
                salida.println(registro.replace(",", " "));
                return;
            }
        }
        salida.println("Elemento no encontrado en la BBDD.");
    }

    private static void handleDelete(BufferedReader entrada, PrintWriter salida) throws IOException {
        String id = entrada.readLine();

        List<String> registros = leerBBDD();
        boolean encontrado = false;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BBDD_FILE))) {
            for (String registro : registros) {
                if (registro.startsWith(id + ",")) {
                    encontrado = true;
                } else {
                    writer.write(registro);
                    writer.newLine();
                }
            }
        }

        if (encontrado) {
            salida.println("Datos eliminados de la BBDD.");
        } else {
            salida.println("Elemento no encontrado en la BBDD.");
        }
    }

    private static List<String> leerBBDD() throws IOException {
        File file = new File(BBDD_FILE);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        List<String> registros = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                registros.add(linea);
            }
        }
        return registros;
    }

    private static void escribirBBDD(String registro) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BBDD_FILE, true))) {
            writer.write(registro);
            writer.newLine();
        }
    }
}