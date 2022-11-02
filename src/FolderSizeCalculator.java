import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class FolderSizeCalculator {
    private final ForkJoinPool commonPool;
    private FileSystem fileSystem;

    public FolderSizeCalculator() {
        this.commonPool = ForkJoinPool.commonPool();
        this.fileSystem = new FileSystem();
    }

    public long calculate(Path folderPath) {
        return commonPool.invoke(new SizeCalculatorTask(folderPath, fileSystem));
    }


    public static class SizeCalculatorTask extends RecursiveTask<Long> {
        private final Path startingPath;
        private final FileSystem fileSystem;

        public SizeCalculatorTask(Path startingPath, FileSystem fileSystem) {
            this.fileSystem = fileSystem;
            this.startingPath = startingPath;
        }

        @Override
        protected Long compute() {
            if(fileSystem.isDirectory(startingPath)) {
                return ForkJoinTask.invokeAll(createSubtasks())
                        .stream()
                        .mapToLong(ForkJoinTask::join)
                        .sum();
            }
            try {
                System.out.println(Thread.currentThread().getName() + " calculating size of " + startingPath);
                long fileSize = fileSystem.size(startingPath);
                System.out.format(ThreadColors.RESET + "%s: %s - %d\n",
                        Thread.currentThread().getName(),
                        startingPath.getFileName(),
                        fileSize);

                return fileSize;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

        private Collection<SizeCalculatorTask> createSubtasks() {
            final List<SizeCalculatorTask> fileTasks = new ArrayList<>();
            try (DirectoryStream<Path> stream = fileSystem.newDirectoryStream(startingPath)) {
                for (Path path : stream) {
                    fileTasks.add(new SizeCalculatorTask(path, fileSystem));
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return fileTasks;
        }
    }


}

