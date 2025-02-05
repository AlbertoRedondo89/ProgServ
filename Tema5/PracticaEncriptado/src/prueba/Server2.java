package prueba;

import tools.*;
import javax.crypto.SecretKey;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;

public class Server2 {
    private static final String KEYSTORE_PASSWORD = "123456";
    private static final String KEYSTORE_FILE = "server_keystore.jks";
    private X509Certificate certificate;
    private PrivateKey privateKey;
    private SecretKey symmetricKey;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public static void main(String[] args) {
        Server2 server = new Server2();
        server.startServer();
    }

    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("Servidor esperando conexiones...");
            Socket socket = serverSocket.accept();
            System.out.println("Cliente conectado.");

            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            loadCertificate();
            sendCertificate();
            receiveAndVerifySymmetricKey();
            receiveEncryptedMessages();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadCertificate() throws Exception {
        KeyStore keyStore = KeyStore.getInstance("JKS");
        try (FileInputStream fis = new FileInputStream(KEYSTORE_FILE)) {
            keyStore.load(fis, KEYSTORE_PASSWORD.toCharArray());
        }
        certificate = (X509Certificate) keyStore.getCertificate("server_cert");
        privateKey = (PrivateKey) keyStore.getKey("server_cert", KEYSTORE_PASSWORD.toCharArray());
    }

    private void sendCertificate() throws IOException {
        out.writeObject(certificate);
        out.flush();
        System.out.println("Certificado enviado al cliente.");
    }

    private void receiveAndVerifySymmetricKey() throws Exception {
        Packet encryptedPacket = (Packet) in.readObject();
        byte[] decryptedKey = RSA_Asimetric.decryptData(encryptedPacket.message, privateKey);

        symmetricKey = new javax.crypto.spec.SecretKeySpec(decryptedKey, "AES");
        SecretKey generatedHash = Hash.passwordKeyGeneration(new String(decryptedKey), 256);
        SecretKey receivedHash = new javax.crypto.spec.SecretKeySpec(encryptedPacket.hash, "AES");

        if (Hash.compareHash(generatedHash, receivedHash)) {
            System.out.println("Clave simétrica recibida y verificada.");
        } else {
            throw new SecurityException("Error de integridad en la clave.");
        }
    }

    private void receiveEncryptedMessages() throws Exception {
        while (true) {
            Packet receivedPacket = (Packet) in.readObject();
            byte[] decryptedMessage = AES_Simetric.decryptData(symmetricKey, receivedPacket.message);
            SecretKey hashReceived = new javax.crypto.spec.SecretKeySpec(receivedPacket.hash, "AES");
            SecretKey hashGenerated = Hash.passwordKeyGeneration(new String(decryptedMessage), 256);

            if (Hash.compareHash(hashGenerated, hashReceived)) {
                String message = new String(decryptedMessage);
                System.out.println("Mensaje recibido: " + message);
                out.writeObject("Mensaje recibido correctamente.");
            } else {
                System.err.println("Error de integridad en el mensaje.");
            }
        }
    }
}
