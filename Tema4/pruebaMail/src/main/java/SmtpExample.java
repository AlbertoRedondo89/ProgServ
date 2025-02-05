import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

public class SmtpExample {
    public static void main(String[] args) {
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
    }
}