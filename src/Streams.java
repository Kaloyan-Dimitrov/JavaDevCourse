import java.util.Arrays;
import java.util.List;

public class Streams {
    public static void main(String[] args) {
        List<String> someBingoNumbers = Arrays.asList(
                "N40", "N36",
                "B12", "B6",
                "G53", "G49", "G60", "g50", "g64",
                "I26", "I17", "I39",
                "O71"
        );

        someBingoNumbers
                .stream()
                .mapToInt((String bingoNumber) -> Integer.decode(bingoNumber.substring(1)))
                .forEach(System.out::println);
    }
}
