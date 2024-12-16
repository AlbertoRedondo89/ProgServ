package tema3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientWorker implements Runnable {
    private Socket client;

    public ClientWorker(Socket c) {
        client = c;
    }

    @Override
    public void run() {
        try {
            while (true) {
                BufferedReader bf = new BufferedReader(new
                        InputStreamReader(client.getInputStream()));
                String linea = bf.readLine();
                PrintWriter pw = new PrintWriter(client.getOutputStream());
                pw.write(linea + " send by SERVER " + "\n");
                pw.flush();
            }
        } catch (IOException ex) {
            System.err.println("tema3.ClientWorker error.");
        }

    }
}
