import java.util.Random;

public class Productor extends Thread {
    private String nombre;
    private Almacen almacen;

    public Productor(String nombre, Almacen almacen) {
        this.nombre = nombre;
        this.almacen = almacen;
    }

    public void run() {
        int actual = 0;
        Random rand = new Random();
        while (true) {
            actual = almacen.producir();
            System.out.println("Productor " + nombre + ": " + actual);
            try {
                sleep(rand.nextInt(1000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
