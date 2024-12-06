package Final;
import java.io.*;
import java.net.*;
import org.json.JSONObject;
import java.util.concurrent.*;

public class ServidorXat {
    private static final int PUERTO = 3000;
    private static final ExecutorService pool = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PUERTO)) {
            System.out.println("Servidor en funcionament al port " + PUERTO);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Nou client connectat.");
                pool.execute(new ClientHandler(socket));
            }
        } catch (IOException e) {
            System.err.println("Error al servidor: " + e.getMessage());
        }
    }
}

class ClientHandler implements Runnable {
    private final Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter salida = new PrintWriter(socket.getOutputStream(), true)) {

            String jsonString;
            while ((jsonString = entrada.readLine()) != null) {
                // Convertir la cadena JSON en un objeto JSON
                JSONObject jsonObject = new JSONObject(jsonString);

                String numOrigen = jsonObject.getString("numOrigen");
                String numDestino = jsonObject.getString("numDestino");
                String texto = jsonObject.getString("texto");

                // Generar el nom de l'arxiu segons els números
                String fileName = generarNomFitxer(numOrigen, numDestino);

                // Escriure el missatge en l'arxiu
                synchronized (ServidorXat.class) {
                    try (FileWriter fw = new FileWriter(fileName, true)) {
                        fw.write(texto + "\n");
                    }
                }

                // Llegir el contingut complet del fitxer
                StringBuilder conversa = new StringBuilder();
                try (BufferedReader fr = new BufferedReader(new FileReader(fileName))) {
                    String linea;
                    while ((linea = fr.readLine()) != null) {
                        conversa.append(linea).append("\n");
                    }
                }

                // Enviar la conversa completa al client
                salida.println(conversa.toString());

                // Terminar si el missatge és "adeu"
                if (texto.equalsIgnoreCase("adeu")) {
                    System.out.println("Finalitzant xat amb " + numOrigen);
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("Error al gestionar el client: " + e.getMessage());
        }
    }

    private String generarNomFitxer(String numOrigen, String numDestino) {
        return "conversa_" + Math.min(numOrigen.hashCode(), numDestino.hashCode()) +
                "_" + Math.max(numOrigen.hashCode(), numDestino.hashCode()) + ".json";
    }
}
