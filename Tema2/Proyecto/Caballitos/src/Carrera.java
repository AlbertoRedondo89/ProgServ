import tools.Tools;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.sleep;

public class Carrera {

    final Object pauseLock = new Object();
    private final ReentrantLock lock = new ReentrantLock(); // Bloqueo para gestionar el acceso
    private final Condition espacioDisponible = lock.newCondition(); // Para notificar caballos esperando
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
    private int caballosRecuperando = 0; // Contador de caballos que están recuperando energía
    private static int contadorFinalizados = 0; // Contador de caballos que han terminado
    volatile boolean carreraEnPausa = false;
    private volatile boolean carreraAcabada = false;
    private Thread[]caballosCorriendo;


    private int dineros;
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

    private void carreraNueva() {
        System.out.println(datosUsuario);
        realizarApuesta();
        iniciaCarrera();
        while (!carreraAcabada) {
            Thread.onSpinWait();
        }
        System.out.printf("\033[%d;1H --------------- %s El ganador de la carrera es:\n", numCaballos+2, ganador.getNombre());
        compruebaApuesta();
    }

    private void realizarApuesta() {
        apuesta = Tools.pideNumero("Introduce tu apuesta", "Apuesta al menos 500 euros, no seas rata.", "No te pases...", dineros, 500);
        caballoApuesta = Tools.pideNumero("Introduce el número de tu caballo", "Ese caballo no existe", "Ese caballo no existe", getNumCaballos(), 0);
    }

    private void compruebaApuesta() {
        if (caballoApuesta == ganador.getLinea()) {
            dineros += (apuesta * numCaballos);
            System.out.println("¡Enhorabuena, has ganado " + (apuesta * numCaballos) + "! ");
        }
        else {
            dineros -= apuesta;
            System.out.println("Lo sentimos, has perdido. Tu saldo es " + dineros);
        }
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

    public int getNumCaballos() {
        return numCaballos;
    }

    private void iniciaCarrera() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
        caballosCorriendo = new Thread[numCaballos];
        for (int i = 0; i < numCaballos; i++) {
            caballosCorriendo[i] = new Thread(new Caballo(distanciaCarrera, caballos[i], i+1, this));
        }
        for (Thread caballo : caballosCorriendo) caballo.start();
    }

    public synchronized void caballoAcabado(Caballo caballo) {
        contadorFinalizados++;
        if (contadorFinalizados == 1) ganador = caballo;
        if (contadorFinalizados == 3) {
            System.out.println("Acabados!");
            pausarCarrera();
        }
        if (contadorFinalizados == numCaballos) carreraAcabada = true;
    }

    public synchronized void pausarCarrera() {
        synchronized (pauseLock) {
            carreraEnPausa = true;
            pauseLock.notifyAll();
        }
    }

    public void reanudarCarrera() {
        synchronized (pauseLock) {
            carreraEnPausa = false;
            pauseLock.notifyAll(); // Notifica a todos los hilos pausados para que continúen.
        }
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
}
