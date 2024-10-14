import java.io.*;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class Main {

    public static String recibir(String a) {
        String respuesta = "";
        respuesta += ("\t el hijo recibe el mensaje : " + a + "\n\t El hijo responde al padre. \n" +
                        "Hijo: envia mensaje al padre: \"Saludos de parde del hijo!");
        return respuesta;
    }


    public static void main(String[] args) throws IOException {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
        String recibido = "";

        try {
            recibido = in.readLine();
            out.write(recibir(recibido));
            out.write("Saludos de parte del hijo!");

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}