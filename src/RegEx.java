import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegEx {
    public static void main(String[] args) {
        String challenge1 = "I want a bike.";
        String challenge2 = "I want a ball.";
        System.out.println(challenge1.matches("I want a b(ike|all)\\."));
        System.out.println(challenge2.matches("I want a b(ike|all)\\."));

        String regexChallenge3 = "I want a \\w+\\.";
        Pattern regexChallenge3Pattern = Pattern.compile(regexChallenge3);
        Matcher regexChallenge3Matcher = regexChallenge3Pattern.matcher(challenge1);

        System.out.println(regexChallenge3Matcher.matches());
        String challenge4 = "Replace all blanks with underscores.";

        System.out.println(challenge4.replaceAll("\\s", "_"));

        String challenge5 = "aaabccccccccdddefffg";
        System.out.println(challenge5.matches("^a{3}bc{8}d{3}ef{3}g$"));

        String challenge7 = "abcd.135";
        System.out.println(challenge7.matches("^[a-zA-Z]+\\.\\d+$"));

        String challenge8 = "abcd.135uvqz.7tzik.999";
        Pattern challenge8Pattern = Pattern.compile("[a-zA-Z]+\\.(\\d+)");
        Matcher challenge8Matcher = challenge8Pattern.matcher(challenge8);
        while(challenge8Matcher.find()) {
            System.out.println(challenge8Matcher.group(1));
        }
        String challenge9 = "abcd.135\tuvqz.7\ttzik.999\n";
        Pattern challenge9Pattern = Pattern.compile("[a-zA-Z]+\\.(\\d+)(\n|\t)");
        Matcher challenge9Matcher = challenge9Pattern.matcher(challenge9);
        while(challenge9Matcher.find()) {
            System.out.println(challenge9Matcher.start(1) + " - " + (challenge9Matcher.end(1) - 1));
        }

        String challenge11 = "{0, 2}, {0, 5}, {1, 3}, {2, 4}";
        Pattern challenge11Pattern = Pattern.compile("\\{(\\d+), (\\d+)\\}");
        Matcher challenge11Matcher = challenge11Pattern.matcher(challenge11);
        while(challenge11Matcher.find()) {
            System.out.println("Point at: " + challenge11Matcher.group(1) + ", " +
                                                challenge11Matcher.group(2));
        }

        String challenge12 = "11111";
        System.out.println(challenge12.matches("^\\d{5}$"));

        String challenge13 = "11111-1111";
        System.out.println(challenge13.matches("^\\d{5}-\\d{4}$"));

        System.out.println(challenge12.matches("^\\d{5}(-\\d{4})?$"));
        System.out.println(challenge13.matches("^\\d{5}(-\\d{4})?$"));
    }
}
