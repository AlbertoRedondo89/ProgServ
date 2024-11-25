import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class ClientDatagram {

    public static void main(String[] args) throws IOException {

        System.setProperty("java.net.preferIPv4Stack", "true");
        InetAddress ip = InetAddress.getByName("172.16.208.1");

        DatagramSocket socket = new DatagramSocket();

        System.out.println("Escribe un String para enviar");
        Scanner sc = new Scanner(System.in);
        String texto = sc.nextLine();

        System.out.println("Paquete enviado");
        DatagramPacket paquet = new DatagramPacket(texto.getBytes(), texto.length(), ip, 2222);
        socket.send(paquet);

        sc.close();
        socket.close();
        System.out.println("Cerrado todo todito todo");
    }



}
