import java.security.PrivateKey;
import java.util.Scanner;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import static java.lang.Thread.sleep;

public class Carrera {
    private int numCaballos;
    private int distanciaCarrera;
    private final ReentrantLock lock = new ReentrantLock(); // Bloqueo para gestionar el acceso
    private final Condition espacioDisponible = lock.newCondition(); // Para notificar caballos esperando
    private int caballosRecuperando = 0; // Contador de caballos que están recuperando energía
    private static int contadorFinalizados = 0; // Contador de caballos que han terminado
    private boolean carreraEnPausa = false; // Bandera para pausar la carrera
    private Caballo ganador;
    private volatile boolean carreraAcabada = false;

    private  Thread[]caballosCorriendo;
    private String[] caballos = {
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
    //ThreadGroup grupoCaballos = new ThreadGroup("caballos");
    private static int contador = 0;

    public Carrera(){}

    public Carrera(int numCaballos, int distanciaCarrera) {
        this.distanciaCarrera = distanciaCarrera;
        this.numCaballos = numCaballos;
        iniciaCarrera();
        while (!carreraAcabada) {
            Thread.onSpinWait();
        }
        System.out.printf("\033[%d;1H --------------- %s El ganador de la carrera es:\n", numCaballos+2, ganador.getNombre());
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
        if (contadorFinalizados >= 3) {
            carreraEnPausa = true;
            System.out.println("Acabados!");
        }
        if (contadorFinalizados == numCaballos) carreraAcabada = true;
    }

    public synchronized void paraCaballo(Caballo caballo) {

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
