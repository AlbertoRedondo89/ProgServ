package tools;

import procesos.Procesos;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Gestor {
    private final String dir = "psp_u1_arv_fill.jar";       // String[] común para llamar a todos los procesos
    private final String[] rutaGenerica = {                 // Cada metodo usará su propio String[] en el que copiara este y añadirá lo que necesita
            "java",
            "-cp",
            dir
    };

    private ArrayList<String> textoWeb = new ArrayList<>(); // Aqui se guarda todo el texto de la web
    private String url;                                     // URL de la web proporcionada por el usuario
    Scanner scan = new Scanner(System.in);

    public Gestor() {

        System.out.println("Introduzca una url web");
        getUrl();

        Menu principal = new Menu("GESTOR DE ARCHIVO WEB", "Cargar web", "Analizar número de caracteres", "Sustituir letra", "Leer entrypted.txt", "Busca palabra clave", "Crear index.html", "Ejecutar index.html");

        int option = -1;
        while (option != 0) {
            option = principal.getOption();
            switch (option) {
                case 0 -> {
                    System.out.println("Hasta luego, buenas tardes.");
                    pass();
                }
                case 1 -> cargarweb();                                                                                  /* LLAMADAS A CADA METODO, SEGUN EL NUMERO PULSADO */
                case 2 -> contarLetras();
                case 3 -> cambiarLetra();
                case 4 -> leerTxt();
                case 5 -> buscaPalabra();
                case 6 -> extraeBody();
                case 7 -> abreIndex();
                default -> System.out.println("bad option");
            }
        }
    }

    private void pass() {
    }

    //------------------------------------------------------------------------------------------------------------------ METODO PARA OBTENER LA URL
    private void getUrl() {
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
    }

    //------------------------------------------------------------------------------------------------------------------METODO PARA COMPROBAR QUE LA URL ES CORRECTA
    private boolean compruebaUrl(String url) {
        boolean check = false;
        String regex = "^(http://|https://)(www\\.)?([a-zA-Z0-9-]+)\\.[a-zA-Z]{2,}(/[a-zA-Z0-9#-._~:/?%&=]*)?$";    //
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(url);
        if (matcher.matches()) check = true;
        return check;
    }

    //------------------------------------------------------------------------------------------------------------------VERIFICA QUE EL TEXTO DE LA WEB NO ESTÉ VACIO
    private boolean checkTexto() {
        if (textoWeb.isEmpty()) {
            System.out.println("Parece que ha habido algún error con la Web, seleccione la opción 1 para cargar la Web");
            return true;
        }
        return false;
    }

    //------------------------------------------------------------------------------------------------------------------METODO PARA CARGAR LA WEB
                                                                                                                      //ENVIARÁ LA URL, RECIBIRA LOS DATOS Y LOS GUARDARA EN TEXTOWEB
    private void cargarweb() {
        if (url.isEmpty()) {    // No debería suceder, pero...
            System.out.println("Parece que ha habido algún error con la url, por favor introdúcela de nuevo.");
            getUrl();
        }
        String[] command = new String[5];
        System.arraycopy(rutaGenerica, 0, command, 0, rutaGenerica.length);
        command[3] = "apps.DescargaWeb";
        command[4] = url;// Pasar la URL como argumento

        Process process = Procesos.ejecutaPrograma(command);
        Procesos.enviar(process, url);
        textoWeb = Procesos.leer(process);
        for (String linea : textoWeb) {
            System.out.println(linea);
        }
    }

    /*----------------------------------------------------------------------------------------------------------------- METODO PARA CONTAR UNA LETRA
                                                                                                                        SOLICITA AL USUARIO LA LETRA QUE QUIERE CONTAR*/
    private void contarLetras() {
        if (checkTexto()) return;
        String buscador;
        do {
            System.out.println("¿Que letra quieres contar en la web?");
            buscador = scan.nextLine();
        }
        while (buscador.isEmpty());
        if (buscador.length() > 1) System.out.println("Sólo se tendrá en cuenta el primer caracter introducido.");

        String[] command = new String[5];
        System.arraycopy(rutaGenerica, 0, command, 0, rutaGenerica.length);
        command[3] = "apps.CuentaCaracteres";
        command[4] = buscador;

        creaProceso(command);
    }

    /*----------------------------------------------------------------------------------------------------------------- METODO PARA CAMBIAR UNA LETRA POR OTRA
    *                                                                                                                   PEDIRÁ AL USUARIO DOS LETRAS PARA SUSTITUIR EN EL TEXTO
    *                                                                                                                   lAS PASARÁ COMO ARGUMENTO CambiarLetras*/
    private void cambiarLetra() {
        if (checkTexto()) return;
        String letraACambiar, nuevaLetra;
        do {
            System.out.println("¿Qué letra quieres sustituir?");
            System.out.println("Tenga en cuenta que cambiar 'b', 'o', 'd' o 'y' generará un html vacío en la opción 6");
            letraACambiar = scan.nextLine();
        }
        while(letraACambiar.isEmpty());
        do {
            System.out.println("¿Por qué letra quieres cambiarla?");
            nuevaLetra = scan.nextLine();}
        while (nuevaLetra.isEmpty());

        String[] command = new String[6];
        System.arraycopy(rutaGenerica, 0, command, 0, rutaGenerica.length);
        command[3] = "apps.CambiaLetras";
        command[4] = letraACambiar;
        command[5] = nuevaLetra;

        creaProceso(command);
    }

    /*----------------------------------------------------------------------------------------------------------------- METODO PARA LEER POR PANTALLA encrypted.txt
    *                                                                                                                   ENVIARA LA RUTA AL ARCHIVO COMO PARÁMETRO
    *                                                                                                                   comprueba la existencia del archivo antes de lanzar el proceso*/
    private void leerTxt() {
        String ruta = "encrypted.txt";
        String checkArchivo = "../psp_u1_arv_fill_jar/encrypted.txt";
        String[] command = new String[5];
        System.arraycopy(rutaGenerica, 0, command, 0, rutaGenerica.length);
        command[3] = "apps.LeeTxt";
        command[4] = ruta;
        File archivo = new File(checkArchivo);
        if (!archivo.exists()) {
            System.out.println("El archivo no existe en la ruta: " + checkArchivo);
            System.out.println("Seleccione la opción 3 para crearlo");
            return; // Salir del metodo si el archivo no existe
        }
        creaProcesoSinTexto(command);
    }

    /*----------------------------------------------------------------------------------------------------------------- METODO PARA BUSCAR UN STRING EN EL TEXTO WEB
    *                                                                                                                   solicita al usuario un String que enviará a PuscaPalabra por argumento*/
    private void buscaPalabra() {
        if (checkTexto()) return;
        String txt;
        do {
            System.out.println("¿Que texto quieres buscar en la web?");
            txt = scan.nextLine();
        }
        while (txt.isEmpty());
        String[] command = new String[5];
        System.arraycopy(rutaGenerica, 0, command, 0, rutaGenerica.length);
        command[3] = "apps.BuscaPalabra";
        command[4] = txt;

        creaProceso(command);
    }

    //------------------------------------------------------------------------------------------------------------------ METODO PARA CREAR EL HTML A PARTIR DE URL
    private void extraeBody() {
        /*
        if (checkTexto()) return;
        String[] command = new String[4];
        System.arraycopy(rutaGenerica, 0, command, 0, rutaGenerica.length);
        command[3] = "apps.ExtraeBody";

        creaProceso(command);
        */
        String ruta = "encrypted.txt";
        String checkArchivo = "../psp_u1_arv_fill_jar/encrypted.txt";
        String[] command = new String[5];
        System.arraycopy(rutaGenerica, 0, command, 0, rutaGenerica.length);
        command[3] = "apps.ExtraeBody";
        command[4] = ruta;
        File archivo = new File(checkArchivo);
        if (!archivo.exists()) {
            System.out.println("El archivo no existe en la ruta: " + checkArchivo);
            System.out.println("Seleccione la opción 3 para crearlo");
            return; // Salir del metodo si el archivo no existe
        }
        creaProcesoSinTexto(command);
    }

    //------------------------------------------------------------------------------------------------------------------ METODO PARA ABRIR EL HTML CON UN NAVEGADOR
    private void abreIndex() {
        String archivoHtml = "index.html";
        String checkArchivo = "../psp_u1_arv_fill_jar/index.html";
        String[] command = new String[5];
        System.arraycopy(rutaGenerica, 0, command, 0, rutaGenerica.length);
        command[3] = "apps.AbreNavegador";
        command[4] = archivoHtml;
        File archivo = new File(checkArchivo);
        if (!archivo.exists()) {
            System.out.println("El archivo no existe en la ruta: " + checkArchivo);
            System.out.println("Seleccione la opción 6 para crearlo");
            return; // Salir del metodo si el archivo no existe
        }
        creaProcesoSinRetorno(command);
    }

    //------------------------------------------------------------------------------------------------------------------ METODOS PARA CREAR PROCESOS NUEVOS
                                                                                                                        // se usa uno u otro segun se necesite enviar o no el texto web
    //                                                                                                                  y según se espere recibir texto de vuelta o no
    private void creaProceso(String[] command) {
        Process process = Procesos.ejecutaPrograma(command);
        System.out.println("Enviando");
        Procesos.enviar(process, String.join("\n",textoWeb));
        try {
            System.out.println("Esperando");
            process.waitFor();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Recibiendo datos");
        ArrayList<String> salida = Procesos.leer(process);
        for (String linea : salida) {
            System.out.println(linea);
        }
    }

    private void creaProcesoSinTexto(String[] command) {
        Process process = Procesos.ejecutaPrograma(command);
        System.out.println("Enviando");
        System.out.println("Recibiendo datos");
        ArrayList<String> salida = Procesos.leer(process);
        for (String linea : salida) {
            System.out.println(linea);
        }
    }
    private void creaProcesoSinRetorno(String[] command) {
        Procesos.ejecutaPrograma(command);
        System.out.println("Enviando");
    }
}
