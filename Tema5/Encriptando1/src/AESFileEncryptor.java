import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.file.*;
import java.security.*;
import java.util.Scanner;

public class AESFileEncryptor {


    /*
    * 2 formas
    * 1 - Eliminar el fichero y crear uno igual pero con otro nombre
    *
    * 2 - Cifrando, lo que toca
    *
    *
    * */

    private static final String AES = "AES";
    private static final int KEY_SIZE = 192; // AES 192 bits
    private static final String EXTENSION = ".aes";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Solicitar el nombre del archivo y la contraseña
        System.out.print("Introduce el nombre del archivo: ");
        String fileName = scanner.nextLine();

        System.out.print("Introduce la contraseña: ");
        String password = scanner.nextLine();

        // Verifica si es un archivo a cifrar o descifrar
        if (fileName.endsWith(EXTENSION)) {
            // Desencriptar
            decryptFile(fileName, password);
        } else {
            // Cifrar
            encryptFile(fileName, password);
        }
    }

    private static void encryptFile(String fileName, String password) {
        try {
            // Leer el archivo original
            File file = new File(fileName);
            if (!file.exists()) {
                System.out.println("El archivo no existe.");
                return;
            }

            // Generar la clave AES a partir de la contraseña
            SecretKeySpec key = generateKey(password);

            // Cifrar el archivo
            byte[] fileData = Files.readAllBytes(file.toPath());
            byte[] encryptedData = encrypt(fileData, key);

            // Borrar el archivo original de forma segura (wipe)
            wipeFile(file);

            // Crear el archivo cifrado con la extensión .aes
            String encryptedFileName = fileName + EXTENSION;
            Files.write(Paths.get(encryptedFileName), encryptedData);
            System.out.println("Archivo cifrado correctamente como " + encryptedFileName);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void decryptFile(String fileName, String password) {
        try {
            // Leer el archivo cifrado
            File file = new File(fileName);
            if (!file.exists()) {
                System.out.println("El archivo no existe.");
                return;
            }

            // Generar la clave AES a partir de la contraseña
            SecretKeySpec key = generateKey(password);

            // Desencriptar el archivo
            byte[] fileData = Files.readAllBytes(file.toPath());
            byte[] decryptedData = decrypt(fileData, key);

            // Borrar el archivo cifrado de forma segura (wipe)
            wipeFile(file);

            // Recuperar el archivo original (sin la extensión .aes)
            String originalFileName = fileName.substring(0, fileName.length() - EXTENSION.length());
            Files.write(Paths.get(originalFileName), decryptedData);
            System.out.println("Archivo descifrado correctamente como " + originalFileName);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static SecretKeySpec generateKey(String password) throws NoSuchAlgorithmException {
        // Utilizamos SHA-256 para derivar una clave de 256 bits de la contraseña
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        byte[] key = sha.digest(password.getBytes());
        return new SecretKeySpec(key, AES);
    }

    private static byte[] encrypt(byte[] data, SecretKeySpec key) throws Exception {
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(data);
    }

    private static byte[] decrypt(byte[] data, SecretKeySpec key) throws Exception {
        Cipher cipher = Cipher.getInstance(AES);
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(data);
    }

    private static void wipeFile(File file) {
        try {
            // Sobrescribir el archivo con datos aleatorios para garantizar que sea eliminado de manera segura
            byte[] randomData = new byte[(int) file.length()];
            new SecureRandom().nextBytes(randomData);
            Files.write(file.toPath(), randomData);
            // Ahora eliminar el archivo
            if (file.delete()) {
                System.out.println("Archivo borrado de forma segura.");
            } else {
                System.out.println("No se pudo borrar el archivo.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
