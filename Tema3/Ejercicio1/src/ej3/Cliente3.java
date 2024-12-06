package ej3;
import java.io.*;
import java.net.*;
import java.util.Scanner;
import org.json.JSONObject;

public class Cliente3 {
    public static void main(String[] args) {
        String host = "localhost";
        int port = 2222;

        try (Socket socket = new Socket(host, port)) {
            System.out.println("Connectat al servidor a " + host + ":" + port);

            PrintWriter sortida = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Scanner scanner = new Scanner(System.in);

            // Demana informació a l'usuari
            System.out.print("Introdueix el carrer: ");
            String carrer = scanner.nextLine();

            System.out.print("Introdueix el codi postal: ");
            String codiPostal = scanner.nextLine();

            System.out.print("Introdueix el país: ");
            String pais = scanner.nextLine();

            System.out.print("Introdueix el número de la casa: ");
            String nombreCasa = scanner.nextLine();

            // Crea el JSON
            JSONObject json = new JSONObject();
            json.put("Carrer", carrer);
            json.put("CodiPostal", codiPostal);
            json.put("Pais", pais);
            json.put("NombreCasa", nombreCasa);

            // Envia el JSON al servidor
            System.out.println("Enviant JSON: " + json.toString());
            sortida.println(json.toString());

            // Llegeix la resposta del servidor
            String resposta = entrada.readLine();
            System.out.println("Resposta del servidor: " + resposta);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
