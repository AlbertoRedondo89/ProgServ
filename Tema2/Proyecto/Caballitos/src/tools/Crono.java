package tools;
/*
public class Crono extends Thread{
    private int segundos;
    private int minutos;
    private boolean encendido;

    public Crono() {
        this.encendido = false;
        this.segundos = 0;
        this.minutos = 0;
    }
    @Override
    public void run() {
        encendido = true;
        while (encendido) {
            try {
                Thread.sleep(1000);
                segundos++;
                if (segundos == 60) {
                    segundos = 0;
                    minutos++;
                }

            } catch (InterruptedException e) {
                System.err.println("El cronómetro fue interrumpido.");
                encendido = false;
            }
        }
    }
    public int getSegundos() {
        return segundos;
    }

    public void setSegundos(int segundos) {
        this.segundos = segundos;
    }

    public int getMinutos() {
        return minutos;
    }

    public void setMinutos(int minutos) {
        this.minutos = minutos;
    }

    public boolean isEncendido() {
        return encendido;
    }

    public void setEncendido(boolean encendido) {
        this.encendido = encendido;
    }

    public String getTiempos() {
        return String.format("%02d:%02d", minutos, segundos);
    }
    public void playPause() {
        if (encendido) encendido = false;
        else encendido = true;
    }
    public void reiniciar() {
        minutos = 0;
        segundos = 0;
    }
*/
public class Crono extends Thread {
    private long inicioTiempo; // Marca del tiempo de inicio
    private long tiempoPausado; // Tiempo total pausado
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
            try {
                Thread.sleep(10); // Pequeña pausa para no saturar la CPU

                if (!pausado) {
                    // Calcular tiempo transcurrido
                    long tiempoActual = System.currentTimeMillis();
                    long tiempoTranscurrido = tiempoActual - inicioTiempo - tiempoPausado;

                    // Convertir el tiempo transcurrido a minutos, segundos y milisegundos
                    long minutos = (tiempoTranscurrido / 60000) % 60;
                    long segundos = (tiempoTranscurrido / 1000) % 60;
                    long milisegundos = tiempoTranscurrido % 1000;

                }
            } catch (InterruptedException e) {
                System.err.println("El cronómetro fue interrumpido.");
                encendido = false;
            }
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

    public boolean isEncendido() {
        return encendido;
    }

    public boolean isPausado() {
        return pausado;
    }

}
