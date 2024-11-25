import tools.Tools;

import java.util.Scanner;

public class Hipodromo {
    // CLASE para gestionar las apuestas, generar carreras e interactuar con el usuario.
    private int dineros = 10000;
    private int apuesta = 0;
    private  int caballoApuesta = 0;
    private Caballo ganador;
    private String datosUsuario;
    private int numCaballos;
    private int distanciaCarrera;
    private Carrera carrera;
    public Hipodromo() {
        puntoDePartida();
    }

    private void puntoDePartida() {
        String banderaDeCuadros =
                        "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢠⣾⣿⣶⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣴⣾⣷⡆⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                        "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣠⣿⣿⣿⣿⣇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢰⣿⣿⣿⣿⣆⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                        "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣠⡴⠾⣿⣿⣿⣿⣿⣿⣿⡄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣿⣿⣿⣿⣿⣿⣿⡷⠦⣄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                        "⠀⠀⠀⠀⠀⠀⠀⠀⠀⢾⣏⠀⠀⢹⣿⣿⣿⣿⣿⣿⣿⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣼⣿⣿⣿⣿⣿⣿⡟⠀⠀⢸⣷⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                        "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⢿⣷⣶⣼⣿⠟⠋⠙⢿⣿⣿⣧⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣸⣿⣿⡿⠋⠙⠻⣿⣧⣴⣾⣿⠃⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                        "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢈⣿⣿⣿⣿⡀⠀⠀⠈⣿⣿⣿⣇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢠⣿⣿⣿⠃⠀⠀⠀⣿⣿⣿⣿⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                        "⢀⣀⣠⣤⣤⣴⣶⠶⠿⠛⠛⢿⣿⣿⣿⣧⠀⠀⠀⣿⣿⣿⣿⡄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣿⣿⣿⣿⡀⠀⠀⣸⣿⣿⣿⣿⠛⠛⠿⠶⣶⣶⣤⣤⣄⣀⡀\n" +
                        "⢿⣿⣿⣿⣿⣷⠀⠀⠀⠀⠀⠘⣿⣿⣿⠟⢷⣶⣾⣿⣿⣿⣿⣿⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣼⣿⣿⣿⣿⣿⣶⡾⠻⣿⣿⣿⠃⠀⠀⠀⠀⠀⣼⣿⣿⣿⣿⣿\n" +
                        "⠘⣿⣿⣿⣿⣿⡆⠀⠀⠀⠀⢀⣼⠛⠁⠀⠀⢻⣿⣿⣿⣿⣿⣿⣧⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣸⣿⣿⣿⣿⣿⣿⡿⠀⠀⠀⠙⣯⣀⠀⠀⠀⠀⢠⣿⣿⣿⣿⣿⠇\n" +
                        "⠀⠸⣿⠿⠛⠛⠛⣦⣶⣶⣾⣿⣿⣇⠀⠀⠀⠀⣿⣿⣿⣿⣿⣿⣿⣇⠀⠀⠀⠀⠀⠀⠀⠀⢠⣿⣿⣿⣿⣿⣿⣿⠁⠀⠀⠀⣰⣿⣿⣿⣶⣶⣴⠟⠛⠛⠿⣿⡏⠀\n" +
                        "⠀⠀⢹⡆⠀⠀⠀⠘⣿⣿⣿⣿⣿⣿⣆⠀⢀⣴⣿⠉⠀⠀⢻⣿⣿⣿⡆⠀⠀⠀⠀⠀⠀⢀⣿⣿⣿⡟⠀⠀⠈⣿⣧⣄⠀⣠⣿⣿⣿⣿⣿⣿⠃⠀⠀⠀⢠⡟⠀⠀\n" +
                        "⠀⠀⠀⢿⡀⠀⠀⠀⢸⣿⣿⡿⠿⠛⠛⣿⣿⣿⣿⡄⠀⠀⠀⢿⣿⣿⣿⡀⠀⠀⠀⠀⠀⣼⣿⣿⣿⠁⠀⠀⢀⣿⣿⣿⣿⠛⠛⠿⠿⣿⣿⣇⠀⠀⠀⢀⡾⠁⠀⠀\n" +
                        "⠀⠀⠀⠈⣿⣶⣶⣾⣿⣇⠀⠀⠀⠀⠀⠘⣿⣿⣿⣿⡀⠀⠀⠘⣿⣿⣿⣷⠀⠀⠀⠀⣸⣿⣿⣿⠇⠀⠀⠀⣾⣿⣿⣿⠃⠀⠀⠀⠀⠀⢨⣿⣷⣶⣶⣿⠃⠀⠀⠀\n" +
                        "⠀⠀⠀⠀⠘⣿⣿⣿⣿⣿⣆⠀⠀⠀⠀⢀⣸⣿⠿⠋⠀⠀⠀⠀⢸⣿⣿⣿⣇⠀⠀⢠⣿⣿⣿⡏⠀⠀⠀⠀⠙⠻⣿⣯⡀⠀⠀⠀⠀⢠⣿⣿⣿⣿⣿⠏⠀⠀⠀⠀\n" +
                        "⠀⠀⠀⠀⠀⠹⣿⣿⣿⣿⣿⡦⠴⠖⠛⠋⠉⠀⠀⠀⠀⠀⠀⠀⠀⢻⣿⣿⣿⡆⢀⣿⣿⣿⡿⠀⠀⠀⠀⠀⠀⠀⠀⠉⠙⠛⠲⠦⢤⣿⣿⣿⣿⣿⡟⠀⠀⠀⠀⠀\n" +
                        "⠀⠀⠀⠀⠀⠀⠉⠉⠉⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⣿⣿⣿⣿⣾⣿⣿⣿⠃⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠉⠉⠀⠀⠀⠀⠀⠀\n" +
                        "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠘⣿⣿⣿⣿⣿⣿⠏⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                        "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢹⣿⣿⣿⣿⡟⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                        "⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠻⢿⡿⠟⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀";


        String mensajeDeBienvenida = "--------------------------------------------\n" +
                "   ¡Bienvenido a la Carrera de caballos  \n" +
                "         haciendo el moonwalk!          \n" +
                "      ¡Que comience la competición!       \n" +
                "--------------------------------------------\n";
        System.out.println(banderaDeCuadros);
        System.out.println(mensajeDeBienvenida);
        carreraNueva();
    }

    // Metodos para generar una carrera y definir sus valores
    private void carreraNueva() {
        datosUsuario = "Tienes un saldo de " + dineros + " euros.";
        ganador = null;
        System.out.println(datosUsuario);
        defineDatosCarrera();
        realizarApuesta();
        carrera = new Carrera(numCaballos, distanciaCarrera, this);
        carrera.start();
        try {
            carrera.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        compruebaApuesta();
    }
    private void defineDatosCarrera() {
        int caballos = Tools.pideNumero("Introduce el número de caballos (mínimo 10)",
                "He dicho que mínimo diez...",
                "No pidas más de 50, que es una carrera, no la carga de los Rohirrim...",
                100, 10);
        int distancia = Tools.pideNumero("¿Cómo de larga quieres que sea la carrera?",
                "Es una carrera, dale más distancia, hombre.",
                "No queremos estar todo el día aqui, no te pases...",
                5000, 200);
        setNumCaballos(caballos);
        setDistanciaCarrera(distancia);
        System.out.println("Número de caballos y distancia configurados.");
    }
    public void setNumCaballos(int numCaballos) {
        if (numCaballos < 10) numCaballos = 10;
        if (numCaballos > 50) numCaballos = 50;
        this.numCaballos = numCaballos;
    }
    public void setDistanciaCarrera(int distanciaCarrera) {
        if (distanciaCarrera < 100) distanciaCarrera = 100;
        if (distanciaCarrera > 5000) distanciaCarrera = 5000;
        this.distanciaCarrera = distanciaCarrera;
    }

    // APUESTA
    private void realizarApuesta() {
        apuesta = Tools.pideNumero("Introduce tu apuesta", "Apuesta al menos 500 euros, no seas rata.", "No te pases...", dineros, 500);
        caballoApuesta = Tools.pideNumero("Introduce el número de tu caballo", "Ese caballo no existe", "Ese caballo no existe", getNumCaballos(), 0);
    }

    private void compruebaApuesta() {
        if (caballoApuesta == ganador.getLinea()) {
            dineros += (apuesta * numCaballos);
            System.out.printf("\033[%d;1H --------------- ¡Enhorabuena, has ganado! %d €\n", numCaballos + 2, (apuesta * numCaballos));
            pedirCarreraNueva();
        }
        else {
            dineros -= apuesta;
            System.out.printf("\033[%d;1H --------------- Lástima, has perdido... Tu saldo es %d €\n", numCaballos + 2, dineros);
            pedirCarreraNueva();
        }
    }

    // Metodo para generar una nueva carrera al terminar la actual
    private void pedirCarreraNueva() {
        datosUsuario = "Tienes " + dineros + " € para apostar";
        System.out.flush();
        String respuesta;
        if (dineros > 500) {
            Scanner get = new Scanner(System.in);
            System.out.printf("\033[%d;1H --------------- %s\n", numCaballos+4, datosUsuario);
            System.out.printf("\033[%d;1H ¿Quieres apostar otra vez?\n", numCaballos+5);
            respuesta = get.nextLine();
            if (respuesta.equalsIgnoreCase("si")) carreraNueva();
            else {
                System.out.println("Gracias por jugar!");
                System.exit(0);
            }
        } else {
            System.out.println("No tienes fondos suficientes, retírate antes de arruinarte... ");
            System.exit(0);
        }
    }


    public int getNumCaballos() {
        return numCaballos;
    }

    public void setGanador(Caballo ganador) {
        this.ganador = ganador;
    }

}
