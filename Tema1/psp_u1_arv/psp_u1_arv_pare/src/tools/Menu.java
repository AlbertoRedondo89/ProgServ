package tools;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
    private String header;
    private String[] options;

    public Menu(String header, String ... options) {
        this.header = header;
        this.options = options;
    }

    public void show()
    {
        System.out.println("=".repeat(18));
        System.out.println(header);
        System.out.println("=".repeat(18));
        int counter = 0;
        for (String option : options)
            System.out.println(++counter + " -> " + option);
        System.out.println("=".repeat(18));
        System.out.println("0 -> Exit");
        System.out.println("=".repeat(18));
    }

    public int getOption()
    {
        Scanner get = new Scanner(System.in);
        show();
        try {
            return get.nextInt();
        }
        catch (InputMismatchException e) {
            System.out.println("Opción incorrecta");
        }
        return -1;
    }

}