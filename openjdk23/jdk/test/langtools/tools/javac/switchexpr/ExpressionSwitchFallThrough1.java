/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/*
 * @test
 * @bug 8206986
 * @summary Check fall through in switch expressions.
 * @compile ExpressionSwitchFallThrough1.java
 * @run main ExpressionSwitchFallThrough1
 */

import java.util.Objects;

public class ExpressionSwitchFallThrough1 {
    public static void main(String... args) {
        new ExpressionSwitchFallThrough1().test();
    }

    private void test() {
        assertEquals("01", printExprFallThrough(0));
        assertEquals("1", printExprFallThrough(1));
        assertEquals("other", printExprFallThrough(3));
        assertEquals("01", printStatementFallThrough(0));
        assertEquals("1", printStatementFallThrough(1));
        assertEquals("other", printStatementFallThrough(3));
    }

    private String printExprFallThrough(Integer p) {
        String result = "";
        return switch (p) {
            case 0: result += "0";
            case 1: result += "1";
                yield result;
            default: yield "other";
        };
    }

    private String printStatementFallThrough(Integer p) {
        String result = "";
        switch (p) {
            case 0: result += "0";
            case 1: result += "1";
                break;
            default: result = "other";
                break;
        }
        return result;
    }

    private static void assertEquals(Object expected, Object actual) {
        if (!Objects.equals(actual, expected)) {
            throw new AssertionError("Unexpected result: " + actual + ", expected: " + expected);
        }
    }
}
