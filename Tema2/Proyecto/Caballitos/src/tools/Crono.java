package tools;
public class Crono extends Thread {

    private long inicioTiempo;
    private long tiempoPausado;
    private boolean encendido;
    private boolean pausado;

    public Crono() {
        this.encendido = false;
        this.pausado = false;
        this.inicioTiempo = 0;
        this.tiempoPausado = 0;
    }

    @Override
    public void run() {
        encendido = true;
        inicioTiempo = System.currentTimeMillis();
        while (encendido) {
        }
    }

    public String getTiempos() {
        long tiempoActual = System.currentTimeMillis();
        long tiempoTranscurrido = tiempoActual - inicioTiempo - tiempoPausado;

        long minutos = (tiempoTranscurrido / 60000) % 60;
        long segundos = (tiempoTranscurrido / 1000) % 60;
        long milisegundos = tiempoTranscurrido % 1000;

        return String.format("%02d:%02d.%03d", minutos, segundos, milisegundos);
    }

    public void playPause() {
        if (pausado) {
            pausado = false;
            inicioTiempo = System.currentTimeMillis() - (tiempoPausado + (System.currentTimeMillis() - inicioTiempo));
        } else {
            pausado = true;
            tiempoPausado += System.currentTimeMillis() - inicioTiempo;
        }
    }
    public void reiniciar() {
        pausado = false;
        encendido = false;
        inicioTiempo = 0;
        tiempoPausado = 0;
    }
}
