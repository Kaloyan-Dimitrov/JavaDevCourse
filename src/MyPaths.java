import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class MyPaths {
    public static void main(String[] args) {
        Path myFile = FileSystems.getDefault().getPath("WorkingDirectory.txt");
        printFile(myFile);
        Path filePath = FileSystems.getDefault().getPath("files", "Subdirectory.txt");
        printFile(filePath);

        filePath = java.nio.file.Paths.get(".");
        System.out.println(filePath.toAbsolutePath());
    }

    private static void printFile(Path path) {
        try(BufferedReader fileReader = Files.newBufferedReader(path)) {
            String line;
            while ((line = fileReader.readLine()) != null) {
                System.out.println(line);
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}






















