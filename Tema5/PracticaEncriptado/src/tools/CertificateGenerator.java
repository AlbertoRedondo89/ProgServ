package tools;

import java.io.FileOutputStream;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.Date;
import javax.security.auth.x500.X500Principal;

import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;

public class CertificateGenerator {
    private static final String KEYSTORE_PASSWORD = "123456";
    private static final String ALIAS = "server_cert";
    private static final String KEYSTORE_FILE = "server_keystore.jks";

    public static void generateAndStoreCertificate() throws Exception {
        // 1. Generar par de claves
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        KeyPair keyPair = keyGen.generateKeyPair();

        // 2. Crear certificado autofirmado
        X509Certificate certificate = generateCertificate(keyPair);

        // 3. Guardar en un KeyStore
        KeyStore keyStore = KeyStore.getInstance("JKS");
        keyStore.load(null, KEYSTORE_PASSWORD.toCharArray());
        keyStore.setKeyEntry(ALIAS, keyPair.getPrivate(), KEYSTORE_PASSWORD.toCharArray(),
                new Certificate[]{certificate});

        try (FileOutputStream fos = new FileOutputStream(KEYSTORE_FILE)) {
            keyStore.store(fos, KEYSTORE_PASSWORD.toCharArray());
        }

        System.out.println("Certificado almacenado en el KeyStore.");
    }

    private static X509Certificate generateCertificate(KeyPair keyPair) throws Exception {
        long now = System.currentTimeMillis();
        Date startDate = new Date(now);
        Date endDate = new Date(now + 365L * 24 * 60 * 60 * 1000); // 1 año de validez
        BigInteger serialNumber = BigInteger.valueOf(now);

        X509v3CertificateBuilder certBuilder = new JcaX509v3CertificateBuilder(
                new X500Principal("CN=Servidor, O=MiEmpresa, C=ES"),
                serialNumber, startDate, endDate,
                new X500Principal("CN=Servidor, O=MiEmpresa, C=ES"),
                keyPair.getPublic());

        ContentSigner signer = new JcaContentSignerBuilder("SHA256withRSA").build(keyPair.getPrivate());

        return new JcaX509CertificateConverter().getCertificate(certBuilder.build(signer));
    }

    public static void main(String[] args) {
        try {
            generateAndStoreCertificate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
