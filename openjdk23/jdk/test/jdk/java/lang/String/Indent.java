/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test
 * @summary Unit tests for String#indent
 * @run main Indent
 */

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Indent {
    static final List<String> ENDS = List.of("", "\n", "   \n", "\n\n", "\n\n\n");
    static final List<String> MIDDLES = List.of(
            "",
            "xyz",
            "   xyz",
            "      xyz",
            "xyz   ",
            "   xyz   ",
            "      xyz   ",
            "xyz\u2022",
            "   xyz\u2022",
            "xyz\u2022   ",
            "   xyz\u2022   ",
            "   // comment"
    );

    public static void main(String[] args) {
        test1();
    }

    /*
     * Test String#indent(int n) functionality.
     */
    static void test1() {
        for (int adjust : new int[] {-8, -7, -4, -3, -2, -1, 0, 1, 2, 3, 4, 7, 8}) {
            for (String prefix : ENDS) {
                for (String suffix : ENDS) {
                    for (String middle : MIDDLES) {
                        String input = prefix + "   abc   \n" + middle + "\n   def   \n" + suffix;
                        String output = input.indent(adjust);

                        Stream<String> stream = input.lines();
                        if (adjust > 0) {
                            final String spaces = " ".repeat(adjust);
                            stream = stream.map(s -> spaces + s);
                        } else if (adjust < 0) {
                            stream = stream.map(s -> s.substring(Math.min(-adjust, indexOfNonWhitespace(s))));
                        }
                        String expected = stream.collect(Collectors.joining("\n", "", "\n"));

                        if (!output.equals(expected)) {
                            report("String::indent(int n)",
                                    "Result indentation not as expected", expected, output);
                        }
                    }
                }
            }
        }
    }

    public static int indexOfNonWhitespace(String s) {
        int left = 0;
        while (left < s.length()) {
            char ch = s.charAt(left);
            if (ch != ' ' && ch != '\t' && !Character.isWhitespace(ch)) {
                break;
            }
            left++;
        }
        return left;
    }


    private static String[] getBody(String[] inLines) {
        int from = -1, to = -1;
        for (int i = 0; i < inLines.length; i++) {
            String line = inLines[i];
            if (!line.isBlank()) {
                if (from == -1) {
                    from = i;
                }
                to = i + 1;
            }
        }
        return Arrays.copyOfRange(inLines, from, to);
    }

    /*
     * Report difference in result.
     */
    static void report(String test, String message, String input, String output) {
        System.err.println("Testing " + test + ": " + message);
        System.err.println();
        System.err.println("Input: length = " + input.length());
        System.err.println("_".repeat(40));
        System.err.print(input.replaceAll(" ", "."));
        System.err.println("_".repeat(40));
        System.err.println();
        System.err.println("Output: length = " + output.length());
        System.err.println("_".repeat(40));
        System.err.print(output.replaceAll(" ", "."));
        System.err.println("_".repeat(40));
        throw new RuntimeException();
    }
}
