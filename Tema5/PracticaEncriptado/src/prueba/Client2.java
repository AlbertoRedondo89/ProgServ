package prueba;
import tools.*;
import javax.crypto.SecretKey;
import java.io.*;
import java.net.Socket;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.Scanner;

public class Client2 {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 5000;
    private PublicKey serverPublicKey;
    private SecretKey symmetricKey;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public static void main(String[] args) {
        new Client2().startClient();
    }

    public void startClient() {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT)) {
            System.out.println("Conectado al servidor.");

            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            receiveAndValidateCertificate();
            sendSymmetricKey();
            handleUserInput();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void receiveAndValidateCertificate() throws Exception {
        X509Certificate cert = (X509Certificate) in.readObject();
        try {
            cert.checkValidity();
            cert.verify(cert.getPublicKey());
            System.out.println("Certificado válido.");
        } catch (Exception e) {
            throw new SecurityException("Certificado inválido o modificado!");
        }

        serverPublicKey = cert.getPublicKey();
        System.out.println("Clave pública extraída y validada.");
    }

    private void sendSymmetricKey() throws Exception {
        symmetricKey = AES_Simetric.keygenKeyGeneration(256);
        byte[] symmetricKeyBytes = symmetricKey.getEncoded();

        SecretKey hashKey = Hash.passwordKeyGeneration(new String(symmetricKeyBytes), 256);
        byte[] encryptedKey = RSA_Asimetric.encryptData(symmetricKeyBytes, serverPublicKey);

        Packet keyPacket = new Packet(encryptedKey, hashKey.getEncoded());
        out.writeObject(keyPacket);
        out.flush();
    }

    private void handleUserInput() throws IOException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Introduce un mensaje: ");
            String message = scanner.nextLine();

            byte[] messageBytes = message.getBytes();
            SecretKey messageHash = Hash.passwordKeyGeneration(message, 256);
            byte[] encryptedMessage = AES_Simetric.encryptData(symmetricKey, messageBytes);

            Packet messagePacket = new Packet(encryptedMessage, messageHash.getEncoded());
            out.writeObject(messagePacket);
            out.flush();

            String serverResponse = (String) in.readObject();
            System.out.println("Servidor: " + serverResponse);
        }
    }
}

