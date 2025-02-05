import java.util.Scanner;

public class Interface {
    private String passw;
    private String nombreArchivo;
    Interface() {
        getArchiv();
        getPassw();
        Encriptador.tratararchivo(nombreArchivo, passw);
    }

    private void getPassw() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduzca su passw: ");
        String password = sc.nextLine();
        this.passw = password;
    }
    private void getArchiv() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Introduzca el nombre del archivo: ");
        String arch = sc.nextLine();
        this.nombreArchivo = arch;
    }
}
