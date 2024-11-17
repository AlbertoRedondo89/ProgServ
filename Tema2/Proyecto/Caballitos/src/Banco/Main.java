package Banco;

public class Main {
    public static void main(String[] args) {
        Banco b = new Banco();
        for (int i = 0; i < 100; i++) {
            EjecutandoTransferencias r = new EjecutandoTransferencias(b, i, 2000);
            Thread t = new Thread(r);
            t.start();
        }
    }
}
