public class HeredaHilo  extends Thread{

    int number;
    String nom;

    public HeredaHilo(String nom) {
        this.nom = nom;
    }

    public void run(){
        number = (int)(Math.random()*100);
        System.out.println(nom + ": iniciat");
        System.out.println(nom + " : valor " + number);
        System.out.println(nom + ": finalitzat");
    }

    public static void main(String[] args) {

        Thread thread;
        String nom = "Fil ";
        for (int i = 1; i < 101; i++) {
            thread = new HeredaHilo(nom + i);
            thread.start();
        }
    }
}