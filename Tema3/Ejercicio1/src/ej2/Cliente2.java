package ej2;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Cliente2 {
    public static void main(String[] args) {
        String destino = "localhost";
        int puertoDestino = 2222;
        Socket socket = new Socket();
        boolean numeroAcertado = false;
        InetSocketAddress direccion = new InetSocketAddress(destino, puertoDestino);
        try {
            socket.connect(direccion);
            while (!numeroAcertado) {
                System.out.println("Adivina el número");
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                String txt = reader.readLine();
                PrintWriter pw = new PrintWriter(socket.getOutputStream());
                pw.print(txt + "\n");
                pw.flush();
                BufferedReader bfr = Cliente2.getFlujo(socket.getInputStream());
                System.out.println("El resultado fue: " + bfr.readLine());
                numeroAcertado = checkResultado(bfr.readLine());
//socket.close();
            }
        } catch (IOException e) {
            System.out.println("Error Client");
        }
    }

    private static boolean checkResultado(String s) {
        if (s.equals("OK")) return true;
        else return false;
    }

    public static BufferedReader getFlujo(InputStream is) {
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader bfr = new BufferedReader(isr);
        return bfr;
    }
}