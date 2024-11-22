import tools.Crono;
import tools.Tools;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Carrera extends Thread{

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
    private ArrayList<Thread> caballosCorriendo = new ArrayList<>();
    private Hipodromo hipodromo;

    private Caballo ganador;

    public Carrera() {}
    public Carrera(int numCaballos, int distanciaCarrera, Hipodromo hipodromo) {
        this.hipodromo = hipodromo;
        setDistanciaCarrera(distanciaCarrera);
        setNumCaballos(numCaballos);
    }
@Override
public void run() {
        iniciaCarrera();
        while (!carreraAcabada) {

        }
    System.out.printf("\033[%d;1H --------------- El ganador de la carrera es: %s\n", numCaballos+2, ganador.getNombre());
}
    // CARRERA
    private void iniciaCarrera() {
        caballosRecuperando = 0;
        contadorFinalizados = 0;
        carreraEnPausa = false;
        carreraAcabada = false;
        ganador = null;
        caballosCorriendo.clear();
        crono.reiniciar();
        System.out.print("\033[H\033[2J");
        System.out.flush();
        for (int i = 0; i < numCaballos; i++) {
            caballosCorriendo.add(new Thread(new Caballo(distanciaCarrera, caballos[i], i+1, this)));
        }
        for (Thread caballo : caballosCorriendo) caballo.start();
        crono.start();
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
        if (contadorFinalizados == 1) {
            ganador = caballo;
            hipodromo.setGanador(caballo);
        }
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
        System.out.printf("\033[%d;1H Han acabado algunos caballos, escribe 'si' para ver terminar la carrera\n", numCaballos+4);
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
            caballo.velocidadRecuperada();
            Thread.sleep(2000);

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
        this.numCaballos = numCaballos;
    }
    public void setDistanciaCarrera(int distanciaCarrera) {
        this.distanciaCarrera = distanciaCarrera;
    }


}
