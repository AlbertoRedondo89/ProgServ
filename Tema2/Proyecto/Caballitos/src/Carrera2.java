import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Carrera2 implements Runnable {
    private final List<Horse> caballos = new ArrayList<>();
    private final AtomicInteger caballosEnMeta = new AtomicInteger(0);
    private volatile boolean continuarCarrera = true;

    public Carrera2(int numeroCaballos) {
        for (int i = 1; i <= numeroCaballos; i++) {
            caballos.add(new Horse(i));
        }
    }

    @Override
    public void run() {
        System.out.println("¡Inicia la carrera!");
        caballos.forEach(h -> new Thread(h).start());

        while (continuarCarrera) {
            // Espera para ver si tres caballos llegaron a la meta
            if (caballosEnMeta.get() >= 3) {
                continuarCarrera = false;
                System.out.println("¡Tres caballos han llegado a la meta!");
                preguntarUsuario();
            }
        }

        detenerCarrera();
    }

    private void detenerCarrera() {
        for (Horse caballo : caballos) {
            caballo.detener();
        }
        System.out.println("La carrera ha terminado.");
    }

    private void preguntarUsuario() {
        // Aquí puedes implementar lógica para preguntar si continuar.
        System.out.println("¿Quieres continuar la carrera? (s/n)");
        // Simular lógica de entrada del usuario y decisión...
    }

    class Horse implements Runnable {
        private final int id;
        private volatile boolean enCarrera = true;

        public Horse(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            while (enCarrera && continuarCarrera) {
                avanzar();
                if (Math.random() > 0.95) { // Condición para cruzar la meta
                    cruzarMeta();
                }

                try {
                    Thread.sleep(100); // Simular avance
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        private void avanzar() {
            System.out.println("Caballo " + id + " avanzando...");
        }

        private void cruzarMeta() {
            if (continuarCarrera && caballosEnMeta.incrementAndGet() <= 3) {
                System.out.println("Caballo " + id + " cruzó la meta!");
            }
            detener();
        }

        public void detener() {
            enCarrera = false;
        }
    }

    public static void main(String[] args) {
        Carrera2 carrera = new Carrera2(5);
        Thread hiloCarrera = new Thread(carrera);
        hiloCarrera.start();
    }
}
