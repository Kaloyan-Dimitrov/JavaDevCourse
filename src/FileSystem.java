import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileSystem {

    public long size(Path path) throws IOException {
        return Files.size(path);
    }

    public DirectoryStream<Path> newDirectoryStream(Path path) throws IOException {
        return Files.newDirectoryStream(path);
    }

    public boolean isDirectory(Path path) {
        System.out.println("Using FileSystem.isDirectory()");
        return Files.isDirectory(path);
    }
}