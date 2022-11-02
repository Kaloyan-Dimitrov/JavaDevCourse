import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        FolderSizeCalculator folderSizeCalculator = new FolderSizeCalculator();
        long folderSize = folderSizeCalculator.calculate(Path.of("extractedZip"));
        System.out.println(ThreadColors.RED + "Folder size: " + folderSize);
    }
}
