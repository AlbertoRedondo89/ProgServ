import java.io.*;
import java.util.GregorianCalendar;
import java.util.Scanner;

public class Main {

    public static String responder(String a) {
        String respuesta = "";
        respuesta += (a + " El hijo respone: Hola papá!");
        return respuesta;
    }


    public static void main(String[] args) throws IOException {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(System.out));
        String recibido = "";

        try {
            recibido = in.readLine();
            out.write(responder(recibido));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        in.close();
        out.close();

    }
}