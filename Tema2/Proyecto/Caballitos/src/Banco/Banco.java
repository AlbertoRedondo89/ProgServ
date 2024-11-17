package Banco;

public class Banco {

    public Banco() {
        cuentas = new double[100];
        for (int i = 0; i < cuentas.length; i++) cuentas[i] = 2000;
    }

    public synchronized void transferencia(int cuentaOrigen, int cuentaDestino, double cantidadDinero) {
        if (cuentas[cuentaOrigen] >= cantidadDinero) {
            System.out.println(Thread.currentThread());
            cuentas[cuentaOrigen] -= cantidadDinero;
            //System.out.println(cantidadDinero + " € de la cuenta " + cuentaOrigen + " a la cuenta " + cuentaDestino);
            System.out.printf("%10.2f de %d para la cuenta %d ", cantidadDinero, cuentaOrigen, cuentaDestino);
            System.out.println();
            cuentas[cuentaDestino] += cantidadDinero;

            //System.out.println(getSaldoTotal());
            System.out.printf("Saldo total: %10.2f%n", getSaldoTotal());
        }
    }

    public double getSaldoTotal() {
        double total = 0;
        for (double d : cuentas) total += d;
        return total;
    }

    private final double [] cuentas;

}
