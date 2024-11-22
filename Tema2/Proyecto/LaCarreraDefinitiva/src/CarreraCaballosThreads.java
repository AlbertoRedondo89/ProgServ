import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class CarreraCaballosThreads {

    private static final int NUM_CABALLOS = 10;
    private static final int META = 100; // Distancia para ganar
    private static final List<Caballo> podio = new ArrayList<>(); // Podio de la carrera
    private static final AtomicBoolean carreraEnCurso = new AtomicBoolean(true); // Control de la carrera

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Crear y lanzar los hilos de los caballos
        List<Caballo> caballos = new ArrayList<>();
        for (int i = 1; i <= NUM_CABALLOS; i++) {
            Caballo caballo = new Caballo(i, META, podio, carreraEnCurso);
            caballos.add(caballo);
            new Thread(caballo).start();
        }

        // Monitorear el progreso
        while (carreraEnCurso.get()) {
            mostrarEstadoCaballos(caballos);

            // Si ya hay tres caballos en el podio, preguntar al usuario si desea continuar
            if (podio.size() == 3) {
                carreraEnCurso.set(false); // Pausar la carrera
                System.out.println("\n¡Tenemos a los tres primeros!");
                System.out.println("1º: Caballo " + podio.get(0).getNumero());
                System.out.println("2º: Caballo " + podio.get(1).getNumero());
                System.out.println("3º: Caballo " + podio.get(2).getNumero());

                System.out.println("\n¿Quieres continuar viendo la carrera? (si/no)");
                String respuesta = scanner.nextLine();

                if (respuesta.equalsIgnoreCase("si")) {
                    carreraEnCurso.set(true); // Continuar la carrera
                } else {
                    System.out.println("La carrera ha terminado. ¡Gracias por jugar!");
                    break;
                }
            }

            // Pausa breve para mostrar el progreso
            try {
                Thread.sleep(500); // Pausa de medio segundo
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Esperar a que todos los caballos terminen
        for (Caballo caballo : caballos) {
            caballo.terminarCarrera();
        }

        // Mostrar resultados finales
        System.out.println("\n¡La carrera ha terminado!");
        System.out.println("Orden de llegada:");
        for (int i = 0; i < podio.size(); i++) {
            System.out.println((i + 1) + "º: Caballo " + podio.get(i).getNumero());
        }
    }

    // Método para mostrar el estado de los caballos
    private static void mostrarEstadoCaballos(List<Caballo> caballos) {
        System.out.println("\nEstado de la carrera:");
        for (Caballo caballo : caballos) {
            System.out.printf("Caballo %d: %s %d metros\n",
                    caballo.getNumero(),
                    generarLineaProgreso(caballo.getPosicion()),
                    caballo.getPosicion());
        }
    }

    // Método para generar una línea de progreso visual
    private static String generarLineaProgreso(int metros) {
        int longitud = metros / 2; // Escalar para que no sea demasiado largo
        return "-".repeat(Math.max(0, longitud)) + ">";
    }
}

// Clase Caballo, implementa Runnable para ejecutarse en un hilo
class Caballo implements Runnable {
    private final int numero; // Identificador del caballo
    private final int meta; // Distancia necesaria para ganar
    private final List<Caballo> podio; // Referencia al podio compartido
    private final AtomicBoolean carreraEnCurso; // Control de la carrera
    private int posicion; // Posición actual del caballo
    private boolean enCarrera; // Si el caballo está activo

    public Caballo(int numero, int meta, List<Caballo> podio, AtomicBoolean carreraEnCurso) {
        this.numero = numero;
        this.meta = meta;
        this.podio = podio;
        this.carreraEnCurso = carreraEnCurso;
        this.posicion = 0;
        this.enCarrera = true;
    }

    public int getNumero() {
        return numero;
    }

    public int getPosicion() {
        return posicion;
    }

    public void terminarCarrera() {
        enCarrera = false;
    }

    @Override
    public void run() {
        Random random = new Random();

        while (enCarrera) {
            // Avanzar solo si la carrera está activa
            if (carreraEnCurso.get()) {
                posicion += random.nextInt(10) + 1; // Avance aleatorio entre 1 y 10

                // Si el caballo alcanza la meta y aún no está en el podio
                if (posicion >= meta && !podio.contains(this)) {
                    synchronized (podio) {
                        if (!podio.contains(this)) { // Verificar doblemente para evitar duplicados
                            podio.add(this);
                            System.out.println("¡Caballo " + numero + " ha cruzado la meta!");
                        }
                    }
                }
            }

            // Pequeña pausa para simular el movimiento
            try {
                Thread.sleep(200); // Pausa de 200 ms
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

