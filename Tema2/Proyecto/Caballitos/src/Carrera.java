import tools.Crono;
import tools.Tools;

import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Carrera {


    private final Crono crono = new Crono();
    final Object pauseLock = new Object();
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition espacioDisponible = lock.newCondition();
    private final String[] caballos = {
            "Pegaso", "Fury", "Shadowfax", "Silver", "Trigger", "Bucephalus",
            "Maximus", "Stormy", "Black Beauty", "Zorro", "Seabiscuit", "Warhorse",
            "Storm Cloud", "Tornado", "Artax", "Cavallo", "Brego", "Rocinante",
            "Epona", "Shire", "Tonto", "Ponyboy", "Hidalgo", "Bullet", "Champion",
            "Man O' War", "Clydesdale", "Thoroughbred", "Flicka", "Blaze", "Joey",
            "Dante", "Spirit", "Rudy", "Buttercup", "Silver Bullet", "Danny",
            "Rocky", "Chester", "Doc", "Lucky", "Goldie", "Flame", "Thunder",
            "Tally", "Apache", "Argo", "Bucephalus II", "Cactus", "Rider", "Jet",
            "Vega", "Cecil", "Lancelot", "Rancher", "Dusty", "Silver Storm"
    };
    private int numCaballos;
    private int distanciaCarrera;
    private int caballosRecuperando = 0;
    private int contadorFinalizados = 0;
    volatile boolean carreraEnPausa = false;
    volatile boolean carreraAcabada = false;
    private Thread[]caballosCorriendo;


    private int dineros = 0;
    private int apuesta = 0;
    private  int caballoApuesta = 0;
    private Caballo ganador;
    private String datosUsuario;

    public Carrera() {}
    public Carrera(int dineros) {
        this.dineros = dineros;
        datosUsuario = "Tienes " + dineros + " € para apostar";
        puntoDePartida();
        carreraNueva();
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
                "       ¡Bienvenido a la Carrera!          \n" +
                "      ¡Que comience la competición!      \n" +
                "--------------------------------------------\n";
        System.out.println(banderaDeCuadros);
        System.out.println(mensajeDeBienvenida);
    }

    private void defineDatosCarrera() {
        int caballos = Tools.pideNumero("Introduce el número de caballos (mínimo 10)",
                "He dicho que mínimo diez...",
                "No pidas más de 100, que es una carrera, no la carga de los Rohirrim...",
                100, 10);
        int distancia = Tools.pideNumero("¿Cómo de larga quieres que sea la carrera?",
                "Es una carrera, dale más distancia, hombre.",
                "No queremos estar todo el día aqui, no te pases...",
                5000, 200);
        setNumCaballos(caballos);
        setDistanciaCarrera(distancia);
        System.out.println("Número de caballos y distancia configurados.");
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
        }
        else {
            dineros -= apuesta;
            System.out.printf("\033[%d;1H --------------- Lástima, has perdido... Tu saldo es %d €\n", numCaballos + 2, dineros);
        }
    }

    // CARRERA
    private void carreraNueva() {
        caballosRecuperando = 0;
        contadorFinalizados = 0;
        carreraEnPausa = false;
        carreraAcabada = false;
        ganador = null;
        System.out.println(datosUsuario);
        defineDatosCarrera();
        realizarApuesta();
        iniciaCarrera();
        while (!carreraAcabada) {
            Thread.onSpinWait();
        }
        System.out.printf("\033[%d;1H --------------- El ganador de la carrera es: %s\n", numCaballos+2, ganador.getNombre());
        compruebaApuesta();
        pedirCarreraNueva();
    }
    private void iniciaCarrera() {
        crono.reiniciar();
        System.out.print("\033[H\033[2J");
        System.out.flush();
        caballosCorriendo = new Thread[numCaballos];
        for (int i = 0; i < numCaballos; i++) {
            caballosCorriendo[i] = new Thread(new Caballo(distanciaCarrera, caballos[i], i+1, this));
        }
        for (Thread caballo : caballosCorriendo) caballo.start();
        crono.start();
    }

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
        }
    }

    public synchronized void pausarCarrera() {
        synchronized (pauseLock) {
            crono.playPause();
            carreraEnPausa = true;
            pauseLock.notifyAll();
        }
    }
    public void reanudarCarrera() {
        synchronized (pauseLock) {
            crono.playPause();
            carreraEnPausa = false;
            pauseLock.notifyAll(); // Notifica a todos los hilos pausados para que continúen.
        }
    }

    // CABALLOS
    public synchronized void caballoAcabado(Caballo caballo) {
        contadorFinalizados++;
        if (contadorFinalizados == 1) ganador = caballo;
        if (contadorFinalizados == 3) {
            pausarCarrera();
            pedirContinuar();
        }
        if (contadorFinalizados == numCaballos) {
            carreraAcabada = true;
            reanudarCarrera();
        }
    }

    private void pedirContinuar() {
        Scanner scanner = new Scanner(System.in);
        String respuesta;
        System.out.printf("\033[%d;1H Han acabado tres caballos, escribe 'si' para ver terminar la carrera\n", numCaballos+4);
        respuesta = scanner.nextLine();
        if (respuesta.equalsIgnoreCase("si")) {
            reanudarCarrera();
        }
        else {
            carreraAcabada = true;
        }
    }

    public String getTimer() {
        return crono.getTiempos();
    }

    public synchronized void caballoDescansando(Caballo caballo) {
        lock.lock(); // Adquirir el bloqueo
        try {
            while (caballosRecuperando >= 2) {
                espacioDisponible.await();
            }

            caballosRecuperando++;
            System.out.printf("\033[%d;1H --------------- %s está recuperando energía!\n", caballo.getLinea(), caballo.getNombre());

            caballo.recuperaEnergia();
            Thread.sleep(3000);

            caballosRecuperando--;
            espacioDisponible.signalAll();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    // GETTERS Y SETTERS
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

    public int getNumCaballos() {
        return numCaballos;
    }

}
