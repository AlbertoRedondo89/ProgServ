
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SmtpExample {
    public static void main(String args[]){

        String to = "albertdesigns89@gmail.com";
        String from = "albertoredondo@paucasesnovescifp.cat";
        String host = "127.0.0.1";

        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);

        Session session = Session.getDefaultInstance(properties);

        try {
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(from));

            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            message.setSubject("Test Mail from Java Program");


            message.setText("You can send mail from Java program by using mail API, but you need" +
                    "couple of more JAR files e.g. smtp.jar and activation.jar");


            Transport.send(message); System.out.println("Email Sent successfully....");
        } catch (MessagingException mex){ mex.printStackTrace(); }

    }
    /*public static void main(String[] args) {
        // Configuración del servidor SMTP
        String host = "smtp.gmail.com"; // Servidor SMTP
        final String username = "b03476860@gmail.com"; // Cambiar por tu correo
        final String password = "12341234aA"; // Cambiar por tu contraseña
        int port = 587; // Puerto SMTP seguro

        // Configuración de propiedades
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", String.valueOf(port));

        // Crear la sesión
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Crear el mensaje
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("b03476860@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("albertoredondo@paucasesnovescifp.cat"));
            message.setSubject("Prueba de SMTP desde Java");
            message.setText("¡Hola! Este es un correo enviado desde una app Java usando SMTP.");

            // Enviar el mensaje
            Transport.send(message);
            System.out.println("Correo enviado exitosamente.");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }*/
}