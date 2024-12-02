import java.util.ArrayList;

public class Almacen {

    int almacenado = 0;
    int max = 10;
    Almacen() {
        Consumidor c1 = new Consumidor("c1", this);
        Consumidor c2 = new Consumidor("c2", this);
        Consumidor c3 = new Consumidor("c3", this);
        Productor p1 = new Productor("p1", this);
        Productor p2 = new Productor("p2", this);
        Productor p3 = new Productor("p3", this);

        p3.start();
        p2.start();
        p1.start();
        c1.start();
        c2.start();
        c3.start();
    }


    public synchronized int consumir() {
        if (almacenado > 0) {
            almacenado--;
        } else {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return almacenado;
    }

    public synchronized int producir() {
        if (almacenado < max) {
            almacenado++;
            notifyAll();
        } else {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return almacenado;
    }
}
