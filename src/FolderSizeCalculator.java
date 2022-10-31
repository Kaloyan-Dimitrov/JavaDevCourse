import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class FolderSizeCalculator {
    private final ForkJoinPool commonPool;
    public final Path folderPath;

    public FolderSizeCalculator(Path folderPath) {
        commonPool = ForkJoinPool.commonPool();
        this.folderPath = folderPath;
    }

    public long calculate() {
        return commonPool.invoke(new SizeCalculatorTask(folderPath));
    }

    public static class SizeCalculatorTask extends RecursiveTask<Long> {
        private final Path startingPath;
        public SizeCalculatorTask(Path startingPath) {
            this.startingPath = startingPath;
        }

        @Override
        protected Long compute() {
            if(Files.isDirectory(startingPath)) {
//                System.out.format(ThreadColors.BLUE + "%s: %s\n",
//                                Thread.currentThread().getName(),
//                                startingPath.getFileName());
                return ForkJoinTask.invokeAll(createSubtasks())
                        .stream()
                        .mapToLong(ForkJoinTask::join)
                        .sum();
            }
            try {
                long fileSize = Files.size(startingPath);
//                    System.out.format(ThreadColors.RESET + "%s: %s - %d\n",
//                            Thread.currentThread().getName(),
//                            startingPath.getFileName(),
//                            fileSize);
                return fileSize;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

        private Collection<SizeCalculatorTask> createSubtasks() {
            final List<SizeCalculatorTask> fileTasks = new ArrayList<>();
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(startingPath)) {
                for (Path path : stream) {
                    fileTasks.add(new SizeCalculatorTask(path));
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return fileTasks;
        }
    }


}

