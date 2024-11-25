import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    static boolean exit = false;
    public static void main(String[] args) {
        int puertoDestino = 2222;

        try {
            ServerSocket serverSocket = new ServerSocket(puertoDestino);

            while (!exit) {
                Socket servidor = serverSocket.accept();
                System.out.println("Conexión establecida");

                BufferedReader reader = new BufferedReader(new InputStreamReader(servidor.getInputStream()));
                String linea = reader.readLine();
                System.out.println("Linea modificada: " + linea);

                PrintWriter writer = new PrintWriter(servidor.getOutputStream());
                writer.write(linea + "\n");
                writer.flush();
                writer.close();
            }

        } catch (IOException e) {
            System.out.println("Error servidor");
        }
    }

}
