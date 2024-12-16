package tema2;

import java.util.concurrent.Semaphore;

public class SemaforoEjemplo {
    public static void main(String[] args) {
        Semaphore semaforo = new Semaphore(2); // Máximo 2 permisos

        // Simulamos hilos que intentan acceder a la sala.
        for (int i = 1; i <= 5; i++) {
            Thread hilo = new Thread(new Tarea(semaforo, "Hilo " + i));
            hilo.start();
        }
    }
}

class Tarea implements Runnable {
    private Semaphore semaforo;
    private String nombre;

    public Tarea(Semaphore semaforo, String nombre) {
        this.semaforo = semaforo;
        this.nombre = nombre;
    }

    @Override
    public void run() {
        try {
            System.out.println(nombre + " esperando para entrar.");
            semaforo.acquire(); // Adquiere un permiso
            System.out.println(nombre + " entra a la sala.");
            Thread.sleep(2000); // Simula el uso del recurso
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            System.out.println(nombre + " sale de la sala.");
            semaforo.release(); // Libera el permiso
        }
    }
}
