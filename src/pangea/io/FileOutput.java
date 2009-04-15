package pangea.io;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * User: Francesco De Nes
 */
public class FileOutput {

    public static void writeFile(String path, String output, boolean overwrite) throws IOException {

        File file = new File(path);

        if (file.exists() && !overwrite) throw new IOException("Output file already exists.");

        FileWriter writer = new FileWriter(file);

        writer.write(output);
        writer.flush();
        writer.close();
    }
}
