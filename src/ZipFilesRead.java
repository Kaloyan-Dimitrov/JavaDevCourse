import java.io.*;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipFilesRead {
    public static void main(String[] args) {
        File zipPath = new File("directions                                                                 .zip");
        File extractionPath = new File("extractedZip");
        inspectZip(zipPath);
//        extractZip(zipPath, extractionPath);
    }

    static void inspectZip(File zipPath) {
        try (ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(zipPath))) {
            ZipEntry zipEntry = null;
            while((zipEntry = zipInputStream.getNextEntry()) != null) {
                System.out.printf("%sFile: %s\nSize: %d\nModified on %TD\nType: %s\n",
                                    ThreadColors.PURPLE,
                                    zipEntry.getName() + ThreadColors.RESET,
                                    zipEntry.getSize(),
                                    new Date(zipEntry.getTime()),
                                    zipEntry.isDirectory() ? "dir" : "file");
            }
        } catch(FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }

    static void extractZip(File zipPath, File outputFolderPath) {
        try (ZipInputStream zipInputStream = new ZipInputStream(new BufferedInputStream(new FileInputStream(zipPath)))) {
            ZipEntry zipEntry = null;
            while((zipEntry = zipInputStream.getNextEntry()) != null) {
                System.out.println(ThreadColors.WHITE + "Extracting " + zipEntry.getName() + "...");
                extractEntry(zipEntry, zipInputStream, outputFolderPath);
            }
        } catch(FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
    }

    static void extractEntry(ZipEntry zipEntry, InputStream zipInputStream, File outputFolderPath) throws IOException {
        File outputFile = new File(outputFolderPath, zipEntry.getName());
        try(FileOutputStream zipOutputStream = new FileOutputStream(outputFile)) {
            writeFile(zipInputStream, zipOutputStream);
        } catch(FileNotFoundException e) {
            System.out.println(ThreadColors.RED + "File was not found: " + e.getMessage() + ThreadColors.RESET);
            try {
                File parent = outputFile.getParentFile();
                if(!parent.isDirectory()) {
                    System.out.println("Creating new directory: " + parent);
                    if(parent.mkdirs()) {
                        FileOutputStream zipOutputStream = new FileOutputStream(outputFile);
                        writeFile(zipInputStream, zipOutputStream);
                    } else throw new IOException("Failed to create directory: " + parent);
                }
            } catch(IOException ioex) {
                System.out.println(ThreadColors.RED + ioex.getMessage() + ThreadColors.RESET);
            }
        }
    }

    static void writeFile(InputStream zipInputStream, OutputStream zipOutputStream) throws IOException {
        byte[] buf = new byte[8192];

        while(zipInputStream.read(buf, 0, buf.length) >= 0) {
            zipOutputStream.write(buf, 0, buf.length);
        }
    }
}
