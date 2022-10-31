import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;


public class LambdaChallenges {
    public static void main(String[] args) {
        Runnable runnable = () -> {
            String myString = "Let's split this up into an array";
            List<String> parts = List.of(myString.split(" "));
            parts.stream()
                    .forEach(System.out::println);
        };

        Function<String, String> everySecondChar = source -> {
            StringBuilder returnVal = new StringBuilder();
            for (int i = 0; i < source.length(); i++) {
                if(i % 2 == 1) returnVal.append(source.charAt(i));
            }
            return returnVal.toString();
        };

        String res = everySecondCharacter(everySecondChar, "1234567890");
        System.out.println(res);

        Supplier<String> iLoveJava = () -> "I love Java!";

        String supplierResult = iLoveJava.get();
        System.out.println(supplierResult);

        List<String> topNames2015 = Arrays.asList(
                "Amelia",
                "Olivia",
                "emily",
                "Isla",
                "Ava",
                "oliver",
                "Jack",
                "Charlie",
                "harry",
                "Jacob"
        );
        topNames2015.stream()
                .map(name -> name.substring(0, 1).toUpperCase()
                            + name.substring(1))
                .peek(System.out::println)
                .sorted();

        long count = topNames2015.stream()
                .map(String::toUpperCase)
                .filter(name -> name.charAt(0) == 'A')
                .count();
        System.out.println(count);
    }

    public static String everySecondCharacter(Function<String, String> everySecondCharacter, String value) {
        return everySecondCharacter.apply(value);
    }
}





