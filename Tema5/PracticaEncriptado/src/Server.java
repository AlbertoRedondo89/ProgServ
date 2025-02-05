import tools.AES_Simetric;
import tools.Hash;
import tools.Packet;
import tools.RSA_Asimetric;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

/*
Clase que crea y maneja el servidor. Lo inicia y lo mantiene a la espera de nuevas conexiones de clientes.
Utiliza las clases auxiliares de tools para encriptar y desencriptar, crear las claves asimétricas y comprobar la integridad
de los hash enviados por el cliente.
 */

public class Server {
    private PublicKey publicKey;
    private PrivateKey privateKey;
    private SecretKey symmetricKey;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public static void main(String[] args) {
        Server server = new Server();
        server.startServer();
    }

    //metodo principal para iniciar el servidor, ponerlo a la espera del cliente
    // y llamar al resto de metodos una vez establecida conexión
    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("prueba.Servidor en espera de conexiones...");
            Socket socket = serverSocket.accept();
            System.out.println("prueba.Cliente conectado.");

            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            generateAndSendPublicKey();
            receiveAndVerifySymmetricKey();
            receiveEncryptedMessages();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Metodo para generar yv enviar la clave publica al cliente
    private void generateAndSendPublicKey() throws IOException {
        KeyPair keyPair = RSA_Asimetric.randomGenerate(2048);
        publicKey = keyPair.getPublic();
        privateKey = keyPair.getPrivate();
        out.writeObject(publicKey);
        out.flush();
        System.out.println("Clave pública enviada al cliente.");
    }

    // Metodo para recibir y verificar la clave simétrica enviada por el cliente.
    // Usa la clave privada para desencriptar el mensaje y poder acceder a la clase simétrica.
    // Llama al metodo compareHash para comprobar integridad del hash
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

    // Metodo para mantener la "conversacion" con el cliente
    private void receiveEncryptedMessages() throws Exception {
        while (true) {
            try {
                Packet receivedPacket = (Packet) in.readObject();
                byte[] decryptedMessage = AES_Simetric.decryptData(symmetricKey, receivedPacket.message);
                SecretKey hashReceived = new javax.crypto.spec.SecretKeySpec(receivedPacket.hash, "AES");
                SecretKey hashGenerated = Hash.passwordKeyGeneration(new String(decryptedMessage), 256);

                if (Hash.compareHash(hashGenerated, hashReceived)) {
                    String message = new String(decryptedMessage);
                    System.out.println("Mensaje recibido: " + message);

                    // Controla la salida del usuario en caso de recibir "exit"
                    if (message.equalsIgnoreCase("exit")) {
                        System.out.println("Cliente solicitó desconexión.");
                        out.writeObject("Desconectando...");
                        out.flush();
                        break;
                    }

                    out.writeObject("Mensaje recibido correctamente.");
                    out.flush();
                } else {
                    System.err.println("Error de integridad en el mensaje.");
                }
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error en la comunicación con el cliente: " + e.getMessage());
                break;
            }
        }
    }
}
