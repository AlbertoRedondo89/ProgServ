package tema3;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public class EchoClient {

    public static BufferedReader getFlujo(InputStream is) {
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader bfr = new BufferedReader(isr);
        return bfr;
    }

    public static void main(String[] args) {
        String local = "localhost";
        int port = 5555;
        Socket socket = new Socket();
        InetSocketAddress direccion = new InetSocketAddress(local, port);
        try {
            socket.connect(direccion);
            while (true) {
                System.out.println("Client text:");
                BufferedReader reader = new BufferedReader(new
                        InputStreamReader(System.in));
                String txt = reader.readLine();
                PrintWriter pw = new PrintWriter(socket.getOutputStream());
                pw.print(txt + "\n");
                pw.flush();
                BufferedReader bfr = EchoClient.getFlujo(socket.getInputStream());
                System.out.println("The return string server has been: " + bfr.readLine());
            }
        } catch (IOException e) {
            System.out.println("Error Client");
        }
    }
}
