package tema2.waiting;

import java.util.Random;

public class Consumidor extends Thread {
    private String nombre;
    private Almacen almacen;

    public Consumidor(String nombre, Almacen almacen) {
        this.nombre = nombre;
        this.almacen = almacen;
    }

    public void run() {
        int actual = 0;
        Random rand = new Random();
        while (true) {
            actual = almacen.consumir();
            System.out.println("waiting.Consumidor " + nombre + ": " + actual);
            try {
                sleep(rand.nextInt(1000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
