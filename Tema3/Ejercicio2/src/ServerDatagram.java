import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.*;

public class ServerDatagram {
    static boolean exit = false;
    public static void main(String[] args) throws IOException {

        System.setProperty("java.net.preferIPv4Stack", "true");
        DatagramSocket socket = new DatagramSocket(2222);

        String missatge = "";

        while (!exit) {
            byte[] buffer = new byte[1024];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);
            missatge = new String(packet.getData(), 0, packet.getLength());
            System.out.println(missatge);
        }
        socket.close();

    }
}
