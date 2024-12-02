package ej1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.InetSocketAddress;

// UDP -> Datagram
// TCP -> Socket

public class Cliente {
    public static void main(String[] args) {
        String destino = "localhost";
        int puertoDestino = 2222;

        Socket socket = new Socket();
        InetSocketAddress direccion = new InetSocketAddress(destino, puertoDestino);

        try {
            socket.connect(direccion);

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String texto = reader.readLine().toUpperCase();

            socket.getOutputStream();
            PrintWriter writer = new PrintWriter(socket.getOutputStream());
            writer.println(texto + "\n");
            writer.flush();

            BufferedReader readerServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String output = readerServer.readLine();

            System.out.println("Recibido del servidor: " + output);

        } catch (IOException e) {
            System.out.println("Error cliente");
        }
    }
}
