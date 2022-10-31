import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;
class UtilitiesTest {
    static Utilities testUtilities;
    @BeforeAll
    static void init() {
        testUtilities = new Utilities();
    }


    @Test
    void everyNthChar() {
        char[] test = "hello".toCharArray();
        assertArrayEquals("el".toCharArray(), testUtilities.everyNthChar(test, 2));
        assertArrayEquals(test, testUtilities.everyNthChar(test, 7));
    }

    @ParameterizedTest
    @CsvSource({"AABCDDEFF,ABCDEF",
            "ABCCABDEEF,ABCABDEF",
            "A,A",
            "AB,AB",
            "AA,A",
            "ABCDEFF,ABCDEF",
            "AB88EFFG,AB8EFG",
            "112233445566,123456",
            "ZYZQQB,ZYZQB",
    })
    void removePairs(String input, String output) {
        assertEquals(output, testUtilities.removePairs(input));
    }

    @Test
    void converter() {
        int a = 10, b = 5;
        assertEquals(300, testUtilities.converter(a, b));
    }

    @Test
    void converterWith0() {
        assertThrows(ArithmeticException.class, () -> testUtilities.converter(10, 0));
    }

    @Test
    void nullIfOddLength() {
        assertNull(testUtilities.nullIfOddLength("123"));
        assertNotNull(testUtilities.nullIfOddLength("1234"));
    }
}






