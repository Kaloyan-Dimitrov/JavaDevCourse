import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        FolderSizeCalculator folderSizeCalculator = new FolderSizeCalculator(Path.of("extractedZip"));
        long folderSize = folderSizeCalculator.calculate();
        System.out.println(ThreadColors.RED + "Folder size: " + folderSize);
    }
}
