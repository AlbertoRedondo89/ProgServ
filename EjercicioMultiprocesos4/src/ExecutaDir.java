import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ExecutaDir {
    public static void main(String[] args) {

        Path dir = Path.of(System.getProperty("user.home"), "Desktop");
        try (BufferedWriter writer = new BufferedWriter(new PrintWriter(new File("archivo.txt")))) {
            muestraDirectorio(dir, 0, writer);
            System.out.println("Archivo creado");
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
        }

    }

    public static void muestraDirectorio(Path dir, int sangrado, BufferedWriter writer) throws IOException {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {

            for (Path element : stream) {

                    writer.write(" ".repeat(sangrado) + element.getFileName() + "\n");
                    writer.flush();

            }
            stream.close();
        }
        catch (IOException ioe) {
            System.err.println("Algo ha sido un verdadero desastre");
        }
    }
}
