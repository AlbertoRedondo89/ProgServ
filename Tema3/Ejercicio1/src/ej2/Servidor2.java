package ej2;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class Servidor2 {
    static boolean exit = false;
    public static void main(String[] args) {
        int puertoDestino = 2222;
        int numero;
        String respuesta;
        try {
            ServerSocket serverSocket = new ServerSocket(puertoDestino);
            Socket server = serverSocket.accept();
            numero = generaRandom();
            System.out.println(numero);
            System.out.println("Conexion recibida!");
            while (!exit) {
//Read From Stream
                InputStream is = server.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader bf = new BufferedReader(isr);
                String linea = bf.readLine();
                OutputStream os = server.getOutputStream();
                PrintWriter pw = new PrintWriter(os);
                respuesta = compruebaNumero(Integer.parseInt(linea), numero);
                pw.write(respuesta);
                pw.flush();
            }
        } catch (IOException e) {
            System.out.println("Error ej1.Server");
        }
    }
    private static String compruebaNumero(int numeroAComprobar, int numerousuario) throws IOException {
       String respuesta = "";

        if (numeroAComprobar == numerousuario){
            respuesta = "OK";
        }
        else if (numeroAComprobar > numerousuario){
            respuesta = "Te pasaste pelotudo";
        }
        else {
            respuesta = "Más aaaalta, coño!";
        }
        return respuesta;
    }
    private static int generaRandom() {
        int rnd = 0;
        Random rand = new Random();
        rnd = rand.nextInt(100);
        return rnd;
    }
}
