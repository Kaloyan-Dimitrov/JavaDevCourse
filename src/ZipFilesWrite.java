import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipFilesWrite {
    public static void main(String[] args) throws IOException {
        List<File> filesToZip = new ArrayList<>(Arrays.asList(
                new File("directions.txt"),
                new File("directions_big.txt")
        ));
        zipFiles(filesToZip, new File("directions.zip"));
    }

    static void zipFiles(List<File> filesToZip, File outputZip) throws IOException {
        try(ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(outputZip))) {
            for (File fileToZip : filesToZip) {
                try (InputStream fileInputStream = new FileInputStream(fileToZip)) {
                    ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
                    zipOutputStream.putNextEntry(zipEntry);

                    byte[] bytes = new byte[8192];
                    while (fileInputStream.read(bytes, 0, bytes.length) >= 0) {
                        zipOutputStream.write(bytes, 0, bytes.length);
                    }
                }
            }
        }
    }
}
