import procesos.Procesos;
import tools.Menu;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static procesos.Procesos.dirPath;

public class Gestor {

    Procesos proceso = new Procesos();

    private ArrayList<String> textoWeb = new ArrayList<>(); // HTML web de la web
    private String url = "";                                // URL web
    Scanner scan = new Scanner(System.in);
    public Gestor() {
        System.out.println("Introduzca una url web");
        do {
            url = scan.nextLine();
            if (compruebaUrl(url)) {
                System.out.println("Dirección web correcta");
            } else {
                System.out.println("Dirección incorrecta");
                System.out.println("La dirección debe empezar con 'http://' o 'https://'");
                System.out.println("Introduzca de nuevo la dirección");
            }
        } while (!compruebaUrl(url));

        Menu principal = new Menu("GESTOR DE ARCHIVO WEB", "Cargar web", "Analizar número de caracteres", "Sustituir letra", "Leer entrypted.txt", "Busca palabra clave", "Crear index.html", "Ejecutar index.html");

        int option = -1;
        while (option != 0) {
            option = principal.getOption();
            switch (option) {
                case 0 -> {
                    System.out.println("A tomar por culo la bicicleta");
                    pass();
                }
                case 1 -> {
                    String[] command = {
                            "java",
                            "-cp",
                            dirPath,
                            "DescargaWeb",
                            url// Pasar la URL como argumento
                    };
                    Process process = Procesos.ejecutaPrograma(command);
                    /* WAITFOR QUITADO POR FALLOS, PENDIENTE DE REVISION
                    try {
                        process.waitFor();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                     */
                    textoWeb = Procesos.leer(process);
                    for(String line : textoWeb) {
                        System.out.println(line);
                    }

                }
                case 2 -> {
                    String buscador = "a";
                    String[] command = {
                            "java",
                            "-cp",
                            dirPath,
                            "CuentaCaracteres",
                            buscador
                    };
                    Process process = Procesos.ejecutaPrograma(command);
                    Procesos.enviar(process, String.join("\n",textoWeb));
                    String resultado = Procesos.leer(process).toString();
                    System.out.println(resultado);
                }
                case 3 -> {
                    String letraACambiar = "a";
                    String nuevaLetra = "b";
                    String[] command = {
                            "java",
                            "-cp",
                            dirPath,
                            "CambiaLetras",
                            letraACambiar,
                            nuevaLetra
                    };
                    Process process = Procesos.ejecutaPrograma(command);
                    Procesos.enviar(process, String.join("\n",textoWeb));
                    String resultado = Procesos.leer(process).toString();
                    System.out.println(resultado);
                }
                case 4 -> {
                }
                case 5 -> {
                }
                case 6 -> {
                }
                case 7 -> {
                }
                default -> System.out.println("bad option");
            }
        }
    }

    private void pass() {
    }

    // METODO PARA COMPROBAR QUE LA URL ES CORRECTA
    private boolean compruebaUrl(String url) {
        boolean check = false;
        String regex = "^(http://|https://)(www\\.)?([a-zA-Z0-9-]+)\\.[a-zA-Z]{2,}(/[a-zA-Z0-9#-._~:/?%&=]*)?$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(url);
        if (matcher.matches()) check = true;
        return check;
    }
}