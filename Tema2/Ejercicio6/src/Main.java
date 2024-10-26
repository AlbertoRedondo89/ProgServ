public class Main {

    public static void main(String[] args) {

        ThreadGroup tg = new ThreadGroup("ThreadGroup");

        Fills fill1 = new Fills(5, "Hijo 1: ");
        Thread thread1 = new Thread(tg, () -> {
            try {
                fill1.Numeros();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        Fills fill2 = new Fills(thread1, "Hijo 2: ");
        Thread thread2 = new Thread(tg, () -> {
            try {
                fill2.Numeros();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        thread1.start();
        thread2.start();

    }

}
