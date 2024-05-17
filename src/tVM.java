import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class tVM {

    public static byte[] loadByteCode(String filename) {
        try {
            Path path = Paths.get(filename);
            return Files.readAllBytes(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {
        String inputFile = args.length > 0 ? args[0] : null;
        boolean debug = args.length > 1 ? args[1].equals("-d") : false;
        try {
            byte[] byteCode = loadByteCode(inputFile);
            ByteReader byteReader = new ByteReader(byteCode, debug);
            byteReader.run();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
