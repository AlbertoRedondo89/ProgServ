import java.util.Random;

import static java.lang.Thread.sleep;

public class Caballo implements Runnable{
    private final int longitudNombre = 15;
    private int velocidad = 50;
    private int modificador = 0;
    private int distanciRecorrida = 0;
    private int longitudPista = 0;
    private String nombre;
    private int linea;
    private int energia = 100;
    Carrera carrera = new Carrera();

    public String getNombre() {
        return nombre.trim();
    }

    public int getLinea() {
        return linea;
    }

    public Caballo(int longitudPista, String nombre, int linea, Carrera carrera) {
        this.longitudPista = longitudPista;
        this.nombre = String.format("%-" + longitudNombre + "s", nombre);
        this.linea = linea;
        this.carrera = carrera;
    }

    public void run() {
        while (distanciRecorrida < longitudPista){
            distanciRecorrida += velocidad;
            modificaVelocidad();
            if (distanciRecorrida > longitudPista) {
                distanciRecorrida = longitudPista;
            }
            int posicionX = distanciRecorrida / 10;
            int longitudTotal = longitudPista / 10;

            String pista = nombre + "_".repeat(posicionX) + "X" + "_".repeat(longitudTotal - posicionX) + "|| ";
            System.out.printf("\033[%d;1H%s%s\n", linea, pista, distanciRecorrida >= longitudPista ? "Meta!" : velocidad + " km/h");

            if (distanciRecorrida >= longitudPista) {
                carrera.caballoAcabado(this);
            }
            modificaEnergia();

            try {
                sleep(900);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void modificaVelocidad() {
        int min = -5;
        int max = 5;
        Random rnd = new Random();
        modificador = rnd.nextInt((max-min) + 1) + min;
        velocidad += modificador;
        if (velocidad > 70) velocidad = 70;
        if (velocidad < 15) velocidad = 15;
    }
    private void modificaEnergia() {
        int min = 1;
        int max = 15;
        Random rnd = new Random();
        energia -= rnd.nextInt((max-min) + 1) + min;
        if (energia <= 0) {
            carrera.paraCaballo(this);
        }
    }

    public void recuperaEnergia() {
        energia = 100;
    }
}
