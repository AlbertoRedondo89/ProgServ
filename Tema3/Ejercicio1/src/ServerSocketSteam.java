import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketSteam {
    static boolean exit = false;
    public static void main(String[] args) {
        int puertoDestino = 5555;
        try {
            ServerSocket serverSocket = new ServerSocket(puertoDestino);
            Socket server = serverSocket.accept();
            while (!exit) {
                System.out.println("Conexion recibida!");
//Read From Stream
                InputStream is = server.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader bf = new BufferedReader(isr);
                String linea = bf.readLine();
                if ("hola".equals(linea)) {
//Write In Stream
                    OutputStream os = server.getOutputStream();
                    PrintWriter pw = new PrintWriter(os);
                    pw.write("adeu" + "\n");
                    pw.flush();
                } else {
//Write In Stream
                    OutputStream os = server.getOutputStream();
                    PrintWriter pw = new PrintWriter(os);
                    pw.write(linea.toUpperCase() + " send by SERVER " + "\n");
                    pw.flush();
                }
            }
        } catch (IOException e) {
            System.out.println("Error ej1.Server");
        }
    }
}