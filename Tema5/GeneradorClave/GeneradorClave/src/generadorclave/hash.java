package generadorclave;

import static generadorclave.GeneradorClave.keygenKeyGeneration;
import java.security.MessageDigest;
import java.util.Arrays;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class hash {

    public static void main(String[] args) {

        System.out.println(passwordKeyGeneration("hola", 192).getEncoded());

        for (int i = 0; i < 9999; i++) {
            String formattedNumber = String.format("%04d", i);
            System.out.println(passwordKeyGeneration(formattedNumber, 192).getEncoded());
        }
    }

    public static SecretKey passwordKeyGeneration(String text, int keySize) {
        SecretKey sKey = null;
        if ((keySize == 128) || (keySize == 192) || (keySize == 256)) {
            try {
                byte[] data = text.getBytes("UTF-8");
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                byte[] hash = md.digest(data);
                byte[] key = Arrays.copyOf(hash, keySize / 8);
                sKey = new SecretKeySpec(key, "AES");
            } catch (Exception ex) {
                System.err.println("Error generant la clau:" + ex);
            }
        }
        return sKey;
    }

}
