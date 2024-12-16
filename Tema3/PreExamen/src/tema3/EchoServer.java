package tema3;

import java.io.IOException;
import java.net.ServerSocket;

public class EchoServer {
    public static void main(String[] args) {
        int PortNumber = 5555;

        try {
            ServerSocket serverSocket = new ServerSocket(PortNumber);
            while (true) {
                ClientWorker w = new ClientWorker(serverSocket.accept());
                Thread t = new Thread(w);
                t.start();
            }
        } catch (IOException e) {
            System.err.println("Error on Echo Server v2");
        }
    }
}
