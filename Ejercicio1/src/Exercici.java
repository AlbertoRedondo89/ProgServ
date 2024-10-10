public class Exercici {

    public boolean parell(int n) {
        return (n % 2 == 0);
    }

    public static void main(String[] args) {
        Exercici s = new Exercici();
        int n = Integer.parseInt(args[0]);

        if (s.parell(n)) System.out.println("El número es par");
        else System.out.println("El número es impar");
    }

}
