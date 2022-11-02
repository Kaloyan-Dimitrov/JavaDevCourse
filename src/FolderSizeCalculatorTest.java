import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.stubbing.Answer;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class FolderSizeCalculatorTest {
    @Mock
    FileSystem fileSystem;
    Path folderPath;
    @InjectMocks
    FolderSizeCalculator folderSizeCalculator;
    private AutoCloseable closeable;
    
    static class MockFileProperties {
        private final long size;
        private final boolean isDirectory;

        public MockFileProperties(long size, boolean isDirectory) {
            this.size = size;
            this.isDirectory = isDirectory;
        }

        public long getSize() {
            return size;
        }

        public boolean isDirectory() {
            return isDirectory;
        }
    }

    static final Map<Path, MockFileProperties> MOCK_FOLDER_HASHMAP = new HashMap<>();

    @BeforeAll
    static void init() {
        MOCK_FOLDER_HASHMAP.put(Path.of("testPath"), new MockFileProperties(4096L, true));
        MOCK_FOLDER_HASHMAP.put(Path.of("testPath/file1.txt"), new MockFileProperties(30L, false));
        MOCK_FOLDER_HASHMAP.put(Path.of("testPath/file2.txt"), new MockFileProperties(20L, false));
        MOCK_FOLDER_HASHMAP.put(Path.of("testPath/innerFolder"), new MockFileProperties(4096L, true));
        MOCK_FOLDER_HASHMAP.put(Path.of("testPath/innerFolder/file3.txt"), new MockFileProperties(22L, false));
        MOCK_FOLDER_HASHMAP.put(Path.of("testPath/innerFolder/file5.txt"), new MockFileProperties(32L, false));
        MOCK_FOLDER_HASHMAP.put(Path.of("testPath/innerFolder/file4.txt"), new MockFileProperties(28L, false));
    }

    @BeforeEach
    void setUp() throws IOException {
        closeable = openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void calculate() throws IOException {
        when(fileSystem.isDirectory(any(Path.class))).thenAnswer((Answer<Boolean>) invocation -> {
            Path pathArgument = (Path) invocation.getArguments()[0];
            return MOCK_FOLDER_HASHMAP.get(pathArgument).isDirectory();
        });
        when(fileSystem.size(any(Path.class))).thenAnswer((Answer<Long>) invocation -> {
            Path pathArgument = (Path) invocation.getArguments()[0];
            return MOCK_FOLDER_HASHMAP.get(pathArgument).getSize();
        });
        when(fileSystem.newDirectoryStream(any(Path.class))).thenAnswer((Answer<DirectoryStream<Path>>) invocation -> {
            String path = invocation.getArguments()[0].toString();
            Iterator<Path> pathChildrenIterator = MOCK_FOLDER_HASHMAP.keySet().stream()
                    .map(Path::toString)
                    .filter(p -> p.startsWith(path))
                    .filter(p -> {
                        String[] currentPathSplit = p.split(Pattern.quote((File.separator)));
                        String[] parentSplit = path.split(Pattern.quote((File.separator)));
                        return currentPathSplit.length == parentSplit.length + 1;
                    })
                    .map(Path::of)
                    .collect(Collectors.toList())
                    .iterator();
            DirectoryStream<Path> directoryStream = mock(DirectoryStream.class);
            when(directoryStream.iterator()).thenReturn(pathChildrenIterator);
            return directoryStream;
        });

        long folderSize = folderSizeCalculator.calculate(Path.of("testPath"));
        System.out.println(ThreadColors.RED + "Folder size: " + folderSize);
    }
}