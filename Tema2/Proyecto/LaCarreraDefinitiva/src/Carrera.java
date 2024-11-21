import java.util.concurrent.atomic.AtomicInteger;

public class Carrera implements Runnable{

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
    private boolean carreraTerminada = false;
    private AtomicInteger contadorFinalizados = new AtomicInteger(0);
    private Caballo ganador;


    public Carrera() {}
    public Carrera(int numCaballos, int distanciaCarrera) {
        this.numCaballos = numCaballos;
        this.distanciaCarrera = distanciaCarrera;
    }

    @Override
    public void run() {
        while (!carreraTerminada) {

        }
    }


    // CABALLOS
    public synchronized void caballoAcabado(Caballo caballo) {
        contadorFinalizados.incrementAndGet();
        if (contadorFinalizados.get() == 1) ganador = caballo;
        if (contadorFinalizados.get() == 3) {
            //System.out.println("Acabados!");
            //pausarCarrera();
        }
        if (contadorFinalizados.get() == numCaballos) carreraTerminada = true;
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
