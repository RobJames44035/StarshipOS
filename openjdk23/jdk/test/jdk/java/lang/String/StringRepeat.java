/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test
 * @summary This exercises String#repeat patterns and limits.
 * @run main/othervm StringRepeat
 */

/*
 * @test
 * @summary This exercises String#repeat patterns with 16 * 1024 * 1024 repeats.
 * @requires os.maxMemory >= 2G
 * @requires vm.bits == "64"
 * @run main/othervm -Xmx2g StringRepeat 16777216
 */

import java.nio.CharBuffer;

public class StringRepeat {
    public static void main(String... args) {
        if (args.length > 0) {
            REPEATS = new int[args.length];
            for (int i = 0; i < args.length; ++i) {
                REPEATS[i] = Integer.parseInt(args[i]);
            }
        }
        test1();
        test2();
    }

    /*
     * Default varitions of repeat count.
     */
    static int[] REPEATS = {
        0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16,
        32, 64, 128, 256, 512, 1024, 64 * 1024, 1024 * 1024
    };

    /*
     * Varitions of Strings.
     */
    static String[] STRINGS = new String[] {
            "", "\0",  " ", "a", "$", "\u2022",
            "ab", "abc", "abcd", "abcde",
            "The quick brown fox jumps over the lazy dog."
    };

    /*
     * Repeat String function tests.
     */
    static void test1() {
        for (int repeat : REPEATS) {
            for (String string : STRINGS) {
                long limit = (long)string.length() * (long)repeat;

                if ((long)(Integer.MAX_VALUE >> 1) <= limit) {
                    break;
                }

                verify(string.repeat(repeat), string, repeat);
            }
        }
    }

    /*
     * Repeat String exception tests.
     */
    static void test2() {
        try {
            "abc".repeat(-1);
            throw new RuntimeException("No exception for negative repeat count");
        } catch (IllegalArgumentException ex) {
            // Correct
        }

        try {
            "abc".repeat(Integer.MAX_VALUE - 1);
            throw new RuntimeException("No exception for large repeat count");
        } catch (OutOfMemoryError ex) {
            // Correct
        }
    }

    static String truncate(String string) {
        if (string.length() < 80) {
            return string;
        }
        return string.substring(0, 80) + "...";
    }

    /*
     * Verify string repeat patterns.
     */
    static void verify(String result, String string, int repeat) {
        if (string.isEmpty() || repeat == 0) {
            if (!result.isEmpty()) {
                System.err.format("\"%s\".repeat(%d)%n", truncate(string), repeat);
                System.err.format("Result \"%s\"%n", truncate(result));
                System.err.format("Result expected to be empty, found string of length %d%n", result.length());
                throw new RuntimeException();
            }
        } else {
            int expected = 0;
            int count = 0;
            for (int offset = result.indexOf(string, expected);
                 0 <= offset;
                 offset = result.indexOf(string, expected)) {
                count++;
                if (offset != expected) {
                    System.err.format("\"%s\".repeat(%d)%n", truncate(string), repeat);
                    System.err.format("Result \"%s\"%n", truncate(result));
                    System.err.format("Repeat expected at %d, found at = %d%n", expected, offset);
                    throw new RuntimeException();
                }
                expected += string.length();
            }
            if (count != repeat) {
                System.err.format("\"%s\".repeat(%d)%n", truncate(string), repeat);
                System.err.format("Result \"%s\"%n", truncate(result));
                System.err.format("Repeat count expected to be %d, found %d%n", repeat, count);
                throw new RuntimeException();
            }
        }
    }
}
