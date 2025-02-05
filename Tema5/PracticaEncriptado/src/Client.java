import tools.AES_Simetric;
import tools.Hash;
import tools.Packet;
import tools.RSA_Asimetric;

import java.io.*;
import java.net.Socket;
import java.security.PublicKey;
import java.util.Scanner;
import javax.crypto.SecretKey;

/*
    Clase que crea y maneja el cliente. Se encarga de conectar con el servidor
    y utiliza las clases auxiliares de tools para encriptar, desencriptar, crear claves simétricas
    y los hash.
*/

public class Client {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 5000;
    private PublicKey serverPublicKey;
    private SecretKey symmetricKey;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public static void main(String[] args) {
        new Client().startClient();
    }

    // Metodo principal para conectar al servidor y llamar al resto de métodos
    public void startClient() {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT)) {
            System.out.println("Conectado al servidor.");

            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            requestPublicKey(); //                                          Solicita clave pública
            sendSymmetricKey(); //                                          Genera y envia clave simétrica
            handleUserInput();  //                                          Captura y envia mensajes del usuario
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //solicitar la clave publica al servidor
    private void requestPublicKey() throws IOException, ClassNotFoundException {
        serverPublicKey = (PublicKey) in.readObject();
        System.out.println("Clave pública recibida del servidor.");
    }

    // Crea la clave simetrica y la envia al servidor
    private void sendSymmetricKey() throws Exception {
        symmetricKey = AES_Simetric.keygenKeyGeneration(256);
        byte[] symmetricKeyBytes = symmetricKey.getEncoded();

        SecretKey hashKey = Hash.passwordKeyGeneration(new String(symmetricKeyBytes), 256);
        byte[] encryptedKey = RSA_Asimetric.encryptData(symmetricKeyBytes, serverPublicKey);

        Packet keyPacket = new Packet(encryptedKey, hashKey.getEncoded());
        out.writeObject(keyPacket);
        out.flush();
    }

    // Metodo para gestionar el envio de mensajes al servidor una vez que se ha establecido la conexion y enviado la clase simetrica
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

            // Salida del bucle en caso de escribir exit. Se hace después de enviar el mensaje para notificar al servidor la salida.
            if (message.equalsIgnoreCase("exit"))  {
                System.out.println("Cerrando la conexión...");
                break;
            }

            String serverResponse = (String) in.readObject();
            System.out.println("prueba.Servidor: " + serverResponse);
        }
    }
}