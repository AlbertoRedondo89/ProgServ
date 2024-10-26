import java.util.Random;

public class Fills extends Thread {

    private String nom;
    private Thread thread = null;
    private Random rndm;
    private int cantidad = 1;

    public Fills(int cantidad, String nom) {
        this.cantidad = cantidad;
        this.nom = nom;
    }
    public Fills(Thread thread, String nom) {
        this.thread = thread;
        this.nom = nom;
    }

    public void Numeros() throws InterruptedException {
        if (thread != null) {
            while (thread.isAlive()) {
                rndm = new Random();
                System.out.println(nom + rndm.nextInt(100));
                Thread.sleep(1000);
            }
        } else {
            while (cantidad > 0) {
                rndm = new Random();
                System.out.println(nom + rndm.nextInt(100));
                cantidad--;
                Thread.sleep(800);
            }
        }
    }
}
