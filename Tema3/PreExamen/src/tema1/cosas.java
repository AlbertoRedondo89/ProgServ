package tema1;

import java.io.*;
import java.nio.Buffer;

public class cosas {

    ProcessBuilder pb = new ProcessBuilder("java", "nombrearchivo");
    Process proces;

    {
        try {
            proces = pb.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    InputStream input = proces.getInputStream();
    InputStreamReader bis = new InputStreamReader(input);
    BufferedReader reader = new BufferedReader(bis);

    OutputStream output = proces.getOutputStream();
    OutputStreamWriter writer = new OutputStreamWriter(output);
    BufferedWriter bw = new BufferedWriter(writer);

    BufferedReader br2 = new BufferedReader(new InputStreamReader(proces.getInputStream()));
    BufferedWriter bw2 = new BufferedWriter(new OutputStreamWriter(proces.getOutputStream()));

}
