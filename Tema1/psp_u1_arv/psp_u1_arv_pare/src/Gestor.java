import procesos.Procesos;
import tools.Menu;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static procesos.Procesos.dirPath;

public class Gestor {

    private ArrayList<String> textoWeb = new ArrayList<>(); // HTML web de la web
    private String url;                                // URL web
    Scanner scan = new Scanner(System.in);
    String[] rutaGenerica = {
            "java",
            "-cp",
            dirPath
    };

    boolean existeIndex = false;
    boolean existeStringWeb = false;
    boolean existeEncrypted = false;

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
                case 1 -> cargarweb();              /* CARGAR WEB */
                case 2 -> contarLetras();           // CONTAR LETRAS
                case 3 -> cambiarLetra();           // CAMBIAR LETRAS
                case 4 -> leerTxt();                // LEE EL ARCHIVO TXT
                case 5 -> buscaPalabra();           // BUSCA UNA PALABRA EN EL STRING DE LA WEB
                case 6 -> extraeBody();             // SACA EL BODY DEL HTML Y CREA UN TXT CON EL
                case 7 -> abreIndex();              // ABRE EL TXT CON EL NAVEGADOR
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

    private void cargarweb() {
        String[] command = new String[5];
        System.arraycopy(rutaGenerica, 0, command, 0, rutaGenerica.length);
        command[3] = "DescargaWeb";
        command[4] = url;// Pasar la URL como argumento

        creaProcesoSinWait(command);
    }
    private void contarLetras() {
        String buscador;
        do {
            System.out.println("¿Que letra quieres contar en la web?");
            buscador = scan.nextLine();
        }
        while (buscador.isEmpty());
        System.out.println("ejecutando");
        String[] command = new String[5];
        System.arraycopy(rutaGenerica, 0, command, 0, rutaGenerica.length);
        command[3] = "CuentaCaracteres";
        command[4] = buscador;
        System.out.println("Llamando al proceso");
        creaProceso(command);
    }

    private void cambiarLetra() {
        String letraACambiar, nuevaLetra;
        do {
            System.out.println("¿Qué letra quieres sustituir?");
            letraACambiar = scan.nextLine();}
        while(letraACambiar.isEmpty());
        do {
        System.out.println("¿Por qué letra quieres cambiarla?");
        nuevaLetra = scan.nextLine();}
        while (nuevaLetra.isEmpty());
        String[] command = new String[6];
        System.arraycopy(rutaGenerica, 0, command, 0, rutaGenerica.length);
        command[3] = "CambiaLetras";
        command[4] = letraACambiar;
        command[5] = nuevaLetra;

        creaProceso(command);
    }
    private void leerTxt() {
        String ruta = System.getProperty("user.dir")+"\\src\\encrypted.txt";
        String[] command = new String[5];
        System.arraycopy(rutaGenerica, 0, command, 0, rutaGenerica.length);
        command[3] = "LeeTxt";
        command[4] = ruta;
        File archivo = new File(ruta);
        if (!archivo.exists()) {
            System.out.println("El archivo no existe en la ruta: " + ruta);
            System.out.println("Seleccione la opción 3 para crearlo");
            return; // Salir del método si el archivo no existe
        }
        creaProcesoSinWait(command);
    }

    private void buscaPalabra() {
        String txt;
        do {
            System.out.println("¿Que texto quieres buscar en la web?");
            txt = scan.nextLine();
        }
        while (txt.isEmpty());
        String[] command = new String[5];
        System.arraycopy(rutaGenerica, 0, command, 0, rutaGenerica.length);
        command[3] = "BuscaPalabra";
        command[4] = txt;

        creaProceso(command);
    }

    private void extraeBody() {
        String[] command = new String[4];
        System.arraycopy(rutaGenerica, 0, command, 0, rutaGenerica.length);
        command[3] = "ExtraeBody";

        creaProceso(command);
    }
    private void abreIndex() {
        String archivo = "src/index.html";
        File archivoHtml = new File(archivo);

        if (archivoHtml.exists()) {
            try {
                Desktop.getDesktop().browse(archivoHtml.toURI());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.err.println("El archivo no esiste");
        }
    }

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
    private void creaProcesoSinWait(String[] command) {
        Process process = Procesos.ejecutaPrograma(command);
        Procesos.enviar(process, String.join("\n",textoWeb));

        ArrayList<String> salida = Procesos.leer(process);
        for (String linea : salida) {
            System.out.println(linea);
        }
    }
}
