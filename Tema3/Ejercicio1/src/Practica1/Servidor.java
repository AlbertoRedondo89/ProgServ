package Practica1;
import java.io.*;
import java.net.*;
import java.util.*;

public class Servidor {
    private static final String BBDD_FILE = "bbdd.txt";

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(2222)) {
            System.out.println("Servidor en espera de conexiones...");

            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                     PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true)) {

                    System.out.println("Client connected.");
                    String command = input.readLine();

                    switch (command.toLowerCase()) {
                        case "insert":
                            handleInsert(input, output);
                            break;
                        case "select":
                            handleSelect(input, output);
                            break;
                        case "delete":
                            handleDelete(input, output);
                            break;
                        default:
                            output.println("Comando desconocido.");
                    }
                } catch (IOException e) {
                    System.err.println("Error manejando cliente: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("No se pudo iniciar el servidor: " + e.getMessage());
        }
    }

    private static synchronized void handleInsert(BufferedReader input, PrintWriter output) throws IOException {
        String id = input.readLine();
        String name = input.readLine();
        String surname = input.readLine();

        List<String> records = readDatabase();
        for (String record : records) {
            if (record.startsWith(id + " ")) {
                output.println("Error: el ID ya existe en la base de datos.");
                return;
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BBDD_FILE, true))) {
            writer.write(id + " " + name + " " + surname);
            writer.newLine();
        }

        output.println("Datos insertados en la BBDD.");
    }

    private static synchronized void handleSelect(BufferedReader input, PrintWriter output) throws IOException {
        String id = input.readLine();

        List<String> records = readDatabase();
        for (String record : records) {
            if (record.startsWith(id + " ")) {
                output.println(record);
                return;
            }
        }

        output.println("Elemento no encontrado en la BBDD.");
    }

    private static synchronized void handleDelete(BufferedReader input, PrintWriter output) throws IOException {
        String id = input.readLine();

        List<String> records = readDatabase();
        boolean found = false;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BBDD_FILE))) {
            for (String record : records) {
                if (record.startsWith(id + " ")) {
                    found = true;
                } else {
                    writer.write(record);
                    writer.newLine();
                }
            }
        }

        if (found) {
            output.println("Dato eliminado de la BBDD.");
        } else {
            output.println("Elemento no encontrado en la BBDD.");
        }
    }

    private static List<String> readDatabase() throws IOException {
        File file = new File(BBDD_FILE);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            List<String> records = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                records.add(line);
            }
            return records;
        }
    }
}
