import javafx.util.Pair;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.stubbing.Answer;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class FolderSizeCalculatorTest {

    static final Map<Path, Pair<Long, Boolean>> pathToSize = new HashMap<>();

    @BeforeAll
    static void init() {
        pathToSize.put(Path.of("extractedZip"), new Pair(4096L, true));
        pathToSize.put(Path.of("extractedZip/directions.txt"), new Pair(32768L, false));
        pathToSize.put(Path.of("extractedZip/JS Developer - Practical Assignment"), new Pair(4096L, true));
        pathToSize.put(Path.of("extractedZip/locations_rand.dat"), new Pair(139264L, false));
        pathToSize.put(Path.of("extractedZip/test"), new Pair(4096L, true));
        pathToSize.put(Path.of("extractedZip/JS Developer - Practical Assignment/.gitignore"), new Pair(8192L, false));
        pathToSize.put(Path.of("extractedZip/JS Developer - Practical Assignment/package.json"), new Pair(8192L, false));
        pathToSize.put(Path.of("extractedZip/JS Developer - Practical Assignment/README.md"), new Pair(8192L, false));
        pathToSize.put(Path.of("extractedZip/JS Developer - Practical Assignment/server.js"), new Pair(8192L, false));
        pathToSize.put(Path.of("extractedZip/test/locations.txt"), new Pair(10834L, false));
        pathToSize.put(Path.of("extractedZip/test/locations_big.txt"), new Pair(17641L, false));
        pathToSize.put(Path.of("extractedZip/test/test2"), new Pair(4096L, true));
        pathToSize.put(Path.of("extractedZip/JS Developer - Practical Assignment/package-lock.json"), new Pair(73728L, false));
        pathToSize.put(Path.of("extractedZip/JS Developer - Practical Assignment/JavaScript Developer Test Task - 2022.pdf"), new Pair(13451264L, false));
        pathToSize.put(Path.of("extractedZip/test/locations.dat"), new Pair(4L, false));
        pathToSize.put(Path.of("extractedZip/test/test2/directions_big.txt"), new Pair(3864L, false));
        pathToSize.put(Path.of("extractedZip/test/test2/directions.txt"), new Pair(5023L, false));
        pathToSize.put(Path.of("extractedZip/test/test2/data.txt"), new Pair(22L, false));
        pathToSize.put(Path.of("extractedZip/test/test2/data.dat"), new Pair(19L, false));
        pathToSize.put(Path.of("extractedZip/test/test2/directions.zip"), new Pair(3327L, false));
    }

    @Test
    void calculate() {
        try (MockedStatic<Files> mockedFiles = mockStatic(Files.class)) {

            mockedFiles.when(() -> Files.isDirectory(any(Path.class))).thenAnswer((Answer<Boolean>) invocation -> {
                String path = invocation.getArguments()[0].toString().replace("\\", "/");
                return pathToSize.get(Path.of(path)).getValue();
            });
            mockedFiles.when(() -> Files.size(any(Path.class))).thenAnswer((Answer<Long>) invocation -> {
                String path = invocation.getArguments()[0].toString().replace("\\", "/");
                return pathToSize.get(Path.of(path)).getKey();
            });

            mockedFiles.when(() -> Files.newDirectoryStream(any(Path.class))).thenAnswer((Answer<DirectoryStream<Path>>) invocation -> {
                String path = invocation.getArguments()[0].toString();
                System.out.println(path);
                List<Path> paths = pathToSize.keySet().stream()
                        .filter(p -> p.toString().startsWith(path))
                        .filter(p -> !p.toString().equals(path))
//                        .peek(System.out::println)
                        .collect(Collectors.toList());
                DirectoryStream<Path> directoryStream = mock(DirectoryStream.class);
                when(directoryStream.iterator()).thenReturn(paths.iterator());
                return directoryStream;
            });

            FolderSizeCalculator folderSizeCalculator = new FolderSizeCalculator(Path.of("extractedZip"));
            long folderSize = folderSizeCalculator.calculate();
            System.out.println(ThreadColors.RED + "Folder size: " + folderSize);

//            mockedFiles.when(Files::isDirectory).thenAnswer(
//                    new Answer() {
//                        public Object answer(InvocationOnMock invocation) {
//                            Object[] args = invocation.getArguments();
//                            Object mock = invocation.getMock();
//                            return false;
////                            return "called with arguments: " + Arrays.toString(args);
//                        }
//                    });
//            mockedFiles.verify(Files);
        }

    }
}