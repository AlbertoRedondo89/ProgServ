package Banco;

public class EjecutandoTransferencias implements Runnable{

    private Banco miBanco = new Banco();
    private int cuentaOrigen;
    private double cantidadMaxima;

    public EjecutandoTransferencias(Banco miBanco, int cuentaOrigen, double cantidadMaxima) {
        this.miBanco = miBanco;
        this.cuentaOrigen = cuentaOrigen;
        this.cantidadMaxima = cantidadMaxima;
    }

    @Override
    public void run() {
        while (true) {
            int cuentaDestino = (int)(Math.random()*100);
            double cantidadAtransferir = cantidadMaxima*Math.random();
            miBanco.transferencia(cuentaOrigen, cuentaDestino,cantidadAtransferir);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
