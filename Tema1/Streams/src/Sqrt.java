import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Sqrt {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        try {
            int read = Integer.parseInt(in.readLine());
            System.out.println("Fill: " + Math.sqrt(read));
        } catch (NumberFormatException e) {
            System.out.println("Hijo: ERROR");
        }
        in.close();
    }
}
