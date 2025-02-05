import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.file.*;
import java.security.*;
import java.util.Arrays;
import java.util.Scanner;

public class Encriptador {
    private static final String EXTENSION = ".aes";

    private static SecretKeySpec secretKey(String pass) {
        SecretKeySpec secretKey = null;
        try {
            byte[] data = pass.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(data);
            byte[] key = Arrays.copyOf(hash, 192/8);
            secretKey = new SecretKeySpec(key, "AES");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        return secretKey;
    }

    private static byte[] encriptar(byte[] archivo, SecretKeySpec secretKey) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return cipher.doFinal(archivo);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private static byte[] desencriptar(byte[] archivo, SecretKeySpec secretKey) {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return cipher.doFinal(archivo);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void tratararchivo(String fileName, String pssw) {
        try {
            File f = new File(fileName);
            if (!f.exists()) {
                System.out.println("El archivo no existe");
                return;
            }
            SecretKeySpec secretKey = secretKey(pssw);

            if (fileName.endsWith(EXTENSION)) {
                byte[] fileData = Files.readAllBytes(f.toPath());
                byte[] encriptado = encriptar(fileData, secretKey);

                wipeFile(f);

                String nombreArchivo = fileName + EXTENSION;
                Files.write(Paths.get(nombreArchivo), encriptado);
                System.out.println("done");
            }
            else {
                byte[] fileData = Files.readAllBytes(f.toPath());
                byte[] desencriptado = desencriptar(fileData, secretKey);

                wipeFile(f);

                String nombreArchivo = fileName + EXTENSION;
                Files.write(Paths.get(nombreArchivo), desencriptado);
                System.out.println("done");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
